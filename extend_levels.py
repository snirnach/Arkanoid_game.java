
"""
extend_levels.py

Reads an Arkanoid level specification file and writes a new file containing
both the original levels and their "Extended" versions.

Usage:
    python extend_levels.py levels.txt

Behavior / assumptions:
- Levels are text sections that contain a header (key:value lines) and a block
  section delimited by the exact markers "START_BLOCKS" and "END_BLOCKS".
- Keys in the header are expected to be in the form:
      key: value   or   key = value
  (whitespace around the separator is allowed). Matching for the keys
  "level_name" and "num_blocks" is case-insensitive.
- The line immediately following "START_BLOCKS" is treated as the first block
  row; that first row is duplicated and inserted as a new top row for the
  Extended version.
- The number of blocks in a row is computed as:
    * if the line contains one or more spaces: the number of space-separated
      tokens (tokens that are not empty);
    * otherwise: the number of characters in the trimmed line.

The script is conservative and preserves most formatting. If `num_blocks` is
missing, the script will compute and insert a num_blocks line for the Extended
version.

No external libraries are used (only standard library: sys, os, re).
"""

import sys
import os
import re


def usage_and_exit():
    print("Usage: python extend_levels.py <input_file>")
    sys.exit(1)


def split_name_ext(path):
    base, ext = os.path.splitext(path)
    if ext == "":
        ext = ".txt"
    return base, ext


def count_blocks_in_row(row: str) -> int:
    """Count blocks in a row: split on whitespace if present, else count chars."""
    s = row.rstrip('\n').strip()
    if s == "":
        return 0
    if " " in s:
        # consider non-empty tokens only
        return len([tok for tok in s.split(" ") if tok != ""])  # type: ignore
    return len(s)


KEY_RE = re.compile(r"^\s*([A-Za-z0-9_]+)\s*[:=]\s*(.*)\s*$")


def parse_header_lines(header_lines):
    """Return a dict of key->(raw_line_index, key, value) for header lines."""
    d = {}
    for idx, line in enumerate(header_lines):
        m = KEY_RE.match(line)
        if m:
            key = m.group(1).strip()
            val = m.group(2).strip()
            d[key.lower()] = (idx, key, val)
    return d


def make_extended_header(header_lines):
    """Return a new header_lines list modified for the Extended version and
    metadata about existing fields.
    """
    parsed = parse_header_lines(header_lines)
    new_header = list(header_lines)

    # Modify level_name if present
    if "level_name" in parsed:
        idx, orig_key, orig_val = parsed["level_name"]
        new_val = orig_val + " (Extended)"
        # replace the line preserving separator style: find separator
        sep_match = re.search(r"[:=]", header_lines[idx])
        if sep_match:
            sep = sep_match.group(0)
        else:
            sep = ":"
        new_header[idx] = f"{orig_key}{sep} {new_val}\n"

    return new_header, parsed


def update_num_blocks_line(header_lines, parsed_header, add_count):
    """Return updated header_lines where num_blocks is increased by add_count.
    If num_blocks doesn't exist, insert it (before START_BLOCKS, i.e., at the end
    of header_lines) with value equal to computed total (we don't know original
    total so assume add_count is the added amount; caller may pass existing total).
    """
    new_header = list(header_lines)
    if "num_blocks" in parsed_header:
        idx, orig_key, orig_val = parsed_header["num_blocks"]
        try:
            orig_num = int(orig_val)
        except ValueError:
            # If parsing fails, leave unchanged but warn
            print(f"Warning: couldn't parse num_blocks value '{orig_val}', skipping update.")
            return new_header
        new_num = orig_num + add_count
        sep_match = re.search(r"[:=]", header_lines[idx])
        sep = sep_match.group(0) if sep_match else ":"
        new_header[idx] = f"{orig_key}{sep} {new_num}\n"
    else:
        # Insert a new num_blocks line at the end of header (before START_BLOCKS)
        new_header.append(f"num_blocks: {add_count}\n")
    return new_header


def process_file(input_path, output_path):
    with open(input_path, "r", encoding="utf-8") as f:
        lines = f.readlines()

    i = 0
    n = len(lines)
    out_lines = []

    last_sep = 0

    while i < n:
        # find START_BLOCKS
        try:
            start_idx = next(j for j in range(i, n) if lines[j].strip() == "START_BLOCKS")
        except StopIteration:
            # no more blocks; copy the remainder and finish
            out_lines.extend(lines[i:])
            break

        # copy any content between i and start_idx (these are header or other lines)
        header_start = i
        header_lines = lines[header_start:start_idx]

        # find END_BLOCKS
        try:
            end_idx = next(j for j in range(start_idx + 1, n) if lines[j].strip() == "END_BLOCKS")
        except StopIteration:
            print("Error: found START_BLOCKS without matching END_BLOCKS. Aborting.")
            return False

        block_lines = lines[start_idx + 1:end_idx]

        # Write original segment (header + START_BLOCKS + block_lines + END_BLOCKS)
        out_lines.extend(header_lines)
        out_lines.append(lines[start_idx])  # START_BLOCKS line
        out_lines.extend(block_lines)
        out_lines.append(lines[end_idx])  # END_BLOCKS line

        out_lines.append("END_LEVEL\n")
        out_lines.append("\n")

        # Build Extended version
        new_header, parsed = make_extended_header(header_lines)

        # Determine first block row and how many blocks in it
        if len(block_lines) == 0:
            added_blocks = 0
            duplicated_block_lines = []
        else:
            # find first non-empty block row
            first_row = None
            for r in block_lines:
                if r.strip() != "":
                    first_row = r
                    break
            if first_row is None:
                added_blocks = 0
                duplicated_block_lines = []
            else:
                added_blocks = count_blocks_in_row(first_row)
                duplicated_block_lines = [first_row]

        # Update num_blocks in new_header
        new_header = update_num_blocks_line(new_header, parsed, added_blocks)

        # Compose extended blocks: duplicate the first row at the top
        extended_blocks = []
        if duplicated_block_lines:
            extended_blocks.extend(duplicated_block_lines)
        extended_blocks.extend(block_lines)

        # Write a separator line between original and extended level for clarity (optional)
        out_lines.append("\n")

        # Write extended header + START_BLOCKS + extended_blocks + END_BLOCKS
        out_lines.extend(new_header)
        out_lines.append(lines[start_idx])  # START_BLOCKS line
        out_lines.extend(extended_blocks)
        out_lines.append(lines[end_idx])  # END_BLOCKS

        # move i past end_idx
        i = end_idx + 1

    # Write output to file
    with open(output_path, "w", encoding="utf-8") as f:
        f.writelines(out_lines)

    return True


def main():
    if len(sys.argv) != 2:
        usage_and_exit()

    input_path = sys.argv[1]
    if not os.path.isfile(input_path):
        print(f"Error: file not found: {input_path}")
        sys.exit(1)

    base, ext = split_name_ext(input_path)
    output_path = f"{base}_extended{ext}"

    ok = process_file(input_path, output_path)
    if not ok:
        print("Processing failed.")
        sys.exit(1)

    print(f"Wrote extended levels to: {output_path}")


if __name__ == "__main__":
    main()

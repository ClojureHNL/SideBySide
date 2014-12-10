def setup
  size 500, 500
  background 0
  fill 255

  @cols = 10
  @rows = 10
  @col_width  = $app.width  / @cols
  @row_height = $app.height / @rows
end

def dark? col, row
  (col.even? and row.even?) || (col.odd? and row.odd?)
end

def draw
  (0...@cols).each do |col|
    (0...@rows).each do |row|
      if dark? col, row
        x = @col_width  * col
        y = @row_height * row
        rect x, y, @col_width, @row_height
      end
    end
  end
end

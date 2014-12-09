def setup
  size 500, 500
  1.upto(25) do |i|
    draw_red(i)
    draw_green(i)
    draw_blue(i)
  end
end

def draw_red(i)
  fill (255 - (10 * i)), 0, 0
  rect 10, (10 * i), 100, 10
end

def draw_green(i)
  fill 0, (5 + (10 * i)), 0
  rect 110, (10 * i), 100, 10
end

def draw_blue(i)
  fill 0, 0, (255 - (10 * i))
  rect 210, (10 * i), 100, 10
end

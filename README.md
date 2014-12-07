# Drawing Side by Side

This is repo contains demo sketches for Processing, implemented in different languages. The primary intent is to show how different programming languages lend themselves to different approaches to solving similar problems. Secondarily, it is intended to demonstrate how to program in Clojure, via comparisons to other languages.

## PREREQUISITES

Here's how to get yourself setup to draw. Get Quil working for the Clojure side, and Ruby-Processing for the Ruby side. The instructions below are "editor agnostic." I use Sublime and Emacs, but you can use whatever you like. These setup instructions leave you free to use the editor of your choice.

### CLOJURE/QUIL

Be sure to install the latest Oracle JVM (1.8 at the time of this writing).

For Clojure / Quil, I follow the following (if possible), before showing up:
- https://github.com/quil/quil/wiki/Installing
- https://github.com/quil/quil/wiki/Dynamic-Workflow-(for-REPL)

For Clojure / Quil, I recommend attempting to understand this approach if you're interested in Functional Programming:
- https://github.com/quil/quil/wiki/Functional-mode-(fun-mode)


### RUBY-PROCESSING

The instructions for installing Ruby-Processing can get a little complicated. But, only because the process differs based on what system you have (Mac, Windows, Linux, Vax, etc.).

For the confident, check out the official install directions: https://github.com/jashkenas/ruby-processing#installation

#### On Arch-Linux

- Install rbenv
- See what the latest jruby is: `rbenv install --list`
- Install the latest jruby via rbenv `rbenv install jruby-<version>` (version = 1.7.16.1 at time of writing)
- Install the ruby-processing gem vai jruby: `jruby -S gem install ruby-processing`
- Download the latest Processing (2.2 at time of writing) and put it somewhere.
- Untar it and note the location of the processing-<version> directory.
- Add `PROCESSING_ROOT: "/home/<your user>/path/to/processing-<version>"` to a ~/.rp5rc file. This tells Ruby-Processing's rp5 command where to find your Processing install.

#### For Mac
You can try to install it with these instructions (or, see below):
https://github.com/jashkenas/ruby-processing/wiki/Installing-ruby-processing-on-the-mac

##### For Mac: Alternatively, here’s what I did on my Mac:

- I had already installed Processing 2.2 from Processing.org: https://processing.org/download/?processing
- (You need to do this if you don't have that already.)
- After you do this, you need to set up a ~/.rp5rc file with the following line in it: `PROCESSING_ROOT: "/Applications/Processing.app/Contents/Java"`
- mkdir my_project
- cd my_project
- install rbenv, I used Homebrew (https://github.com/sstephenson/rbenv)
- rbenv install jruby-1.7.16
- rbenv local jruby-1.7.16
- jruby -S gem install ruby-processing


## DATA

Since there's a [bike-share](http://www.bikesharehawaii.org) effort on the move in Honolulu. Let's get warmed up for bike data by borrowing some from San Francisco. They recently had [an event](http://www.bayareabikeshare.com/datachallenge) to promote working with [their data](https://s3.amazonaws.com/trackerdata/201402_babs_open_data.zip). You can download it and get a head start. We'll play around with this a bit during the workshop as time allows.


## CODA

If Ruby's not your thing, Kevin just sent over this quite thorough [comparison of Python and Clojure](http://www.pixelmonkey.org/2014/11/02/clojonic).

Also, here's Processing in other languages:
- [Vanilla Processing in Java](https://processing.org)
- [Processing.py: Processing in Python](https://github.com/jdf/processing.py)
- [Processing in JavaScript](http://processingjs.org)
- [P5.js: A Newer Processing in JavaScript](http://p5js.org)
- [Quil-CLJS: Processing in ClojureScript (also Quil)](https://github.com/quil/quil/wiki/ClojureScript)
- [Gloss: Approaches P5 in Haskell](http://gloss.ouroborus.net)
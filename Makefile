default: handout-2012-04.html

all: default handout-2012-04.pdf

%.html: %.md
	pandoc -s -r markdown -w html $< >$@

%.pdf: %.md
	pandoc -s -r markdown -w latex $< >$*.tex
#	sed -i 's/\[htbp\]/[bp]/' $*.tex
	texi2pdf $*.tex

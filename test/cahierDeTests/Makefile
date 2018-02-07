FILE=cahier_de_tests_MonkeyIsland_TEST_DDMP

TEX=$(FILE).tex
DVI=$(FILE).dvi
PDF=$(FILE).pdf

VIEWER=acroread

# CONTROLE 
view: $(PDF)
	$(VIEWER) $(PDF) &

$(PDF): $(DVI)
	dvipdf $(DVI)

$(DVI): $(TEX) 
	(latex $(TEX) && latex $(TEX) && latex $(TEX)) || ($(MAKE) clean; exit 1)

clean:
	rm -f $(DVI) $(PDF) *.log *.out *.aux *.toc *.nav *.snm *.vrb *.blg *.bbl *.lof *.dvi
        
new : clean view


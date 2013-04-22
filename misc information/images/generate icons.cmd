@echo off
rem Generates a bunch of mana symbol and other icon images.  Expects the following
rem images to be a solid block of a single color representing the appropriate color to
rem use for the "main circle" of the symbol:
rem icons\resources\white.png
rem icons\resources\blue.png
rem icons\resources\black.png
rem icons\resources\red.png
rem icons\resources\green.png
rem icons\resources\colorless.png
rem icons\resources\untap.png

rem also expects resources\wheel.png to represent the five-color color indicator, at any size
rem (it will be resized).

pushd icons

echo Generating colored circles
convert -size 200x200 xc:black -fill white -draw "circle 102,95 102,5" circleMask.png
convert resources\white.png -resize 200x200^! whiteBlock.png
convert resources\blue.png -resize 200x200^! blueBlock.png
convert resources\black.png -resize 200x200^! blackBlock.png
convert resources\red.png -resize 200x200^! redBlock.png
convert resources\green.png -resize 200x200^! greenBlock.png
convert resources\colorless.png -resize 200x200^! colorlessBlock.png
convert resources\tap.png -resize 200x200^! tapBlock.png
convert resources\untap.png -resize 200x200^! untapBlock.png
convert resources\wheel.png -resize 200x200^! wubrg.png 

rem the extent and resize control the radial position and thickness of the shading, respectively
rem to bring the position inward, increase the extent
rem to make the shading thicker, increase the resize
rem convert circleMask.png -gravity center -background black -extent 240x240 -resize 100x100^! shading.png
rem convert shading.png -shade 270x15 shading.png
rem convert shading.png -resize 200x200^! shading.png
rem convert shading.png -modulate 200 shading.png
rem convert shading.png -sigmoidal-contrast 2,50%% shading.png

convert -size 200x200 xc:black ^
	-fill white -draw "circle 102,95 102,5" ^
	-fill black -draw "circle 102,95 102,15" ^
	shadeMask.png

convert -size 200x200 xc:black shading.png

call :mask shading shadeMask shading
del /F /Q shadeMask.png

call :makeCircle whiteBlock whiteCircle
call :makeCircle blueBlock blueCircle
call :makeCircle blackBlock blackCircle
call :makeCircle redBlock redCircle
call :makeCircle greenBlock greenCircle
call :makeCircle colorlessBlock colorlessCircle
call :makeCircle untapBlock untapCircle
convert -size 200x200 xc:transparent -fill black -draw "circle 98,105 98,195" shadow.png
del /F /Q *Block.png

convert -size 200x200 xc:transparent -fill black -draw "polygon 200,0 0,0 0,200" hybridMask.png
call :overlay circleMask hybridMask hybridMask

echo Generating half-symbols
call :mask whiteCircle hybridMask whiteHalf
call :mask blueCircle hybridMask blueHalf
call :mask blackCircle hybridMask blackHalf
call :mask redCircle hybridMask redHalf
call :mask greenCircle hybridMask greenHalf
del /F /Q hybridMask.png

echo Generating hybrid symbols
call :overlay whiteCircle blueHalf whiteBlueCircle
call :overlay whiteCircle blackHalf whiteBlackCircle
call :overlay blueCircle blackHalf blueBlackCircle
call :overlay blueCircle redHalf blueRedCircle
call :overlay blackCircle redHalf blackRedCircle
call :overlay blackCircle greenHalf blackGreenCircle
call :overlay redCircle greenHalf redGreenCircle
call :overlay redCircle whiteHalf redWhiteCircle
call :overlay greenCircle whiteHalf greenWhiteCircle
call :overlay greenCircle blueHalf greenBlueCircle

echo Generating color indicators
call :overlay whiteCircle shading wIndicator
call :overlay blueCircle shading uIndicator
call :overlay blackCircle shading bIndicator
call :overlay redCircle shading rIndicator
call :overlay greenCircle shading gIndicator
call :overlay whiteBlueCircle shading wuIndicator
call :overlay whiteBlackCircle shading wbIndicator
call :overlay blueBlackCircle shading ubIndicator
call :overlay blueRedCircle shading urIndicator
call :overlay blackRedCircle shading brIndicator
call :overlay blackGreenCircle shading bgIndicator
call :overlay redGreenCircle shading rgIndicator
call :overlay redWhiteCircle shading rwIndicator
call :overlay greenWhiteCircle shading gwIndicator
call :overlay greenBlueCircle shading guIndicator
rem the outline on the five-color one just intrudes too much no matter how small you make it
move wubrg.png wubrgIndicator.png
del /F /Q shading.png
del /F /Q circleMask.png

echo Applying shadow
call :overlay shadow whiteCircle w
call :overlay shadow blueCircle u
call :overlay shadow blackCircle b
call :overlay shadow redCircle r
call :overlay shadow greenCircle g
call :overlay shadow whiteBlueCircle wu
call :overlay shadow whiteBlackCircle wb
call :overlay shadow blueBlackCircle ub
call :overlay shadow blueRedCircle ur
call :overlay shadow blackRedCircle br
call :overlay shadow blackGreenCircle bg
call :overlay shadow redGreenCircle rg
call :overlay shadow redWhiteCircle rw
call :overlay shadow greenWhiteCircle gw
call :overlay shadow greenBlueCircle gu
call :overlay shadow colorlessCircle colorless
del /F /Q shadow.png

echo Generating phyrexian symbols

rem ... old command for generating the circle-and-line symbol, just in case: ...
rem convert -size 200x200 xc:transparent ^
rem 	-stroke black -strokewidth 10 ^
rem 	-draw "line 102,15 102,175" ^
rem 	-fill none -draw "circle 102,95 102,50" ^
rem 	p.png
rem ...
convert -size 200x200 xc:transparent ^
	-stroke black -fill black -draw "circle 102,95 102,65" ^
	p.png

call :overlay w p wp
call :overlay u p up
call :overlay b p bp
call :overlay r p rp
call :overlay g p gp
rem lighten up the black phyrexian symbol a bit so the dot is noticeable
convert bp.png -fill "#808080" -draw "color 50,50 floodfill" bp.png

echo Generating colorless and tap symbols
mkdir numbers
call :makenumber 0
call :makenumber 1
call :makenumber 2
call :makenumber 3
call :makenumber 4
call :makenumber 5
call :makenumber 6
call :makenumber 7
call :makenumber 8
call :makenumber 9
call :makenumber 10
call :makenumber 11
call :makenumber 12
call :makenumber 13
call :makenumber 14
call :makenumber 15
call :makenumber 16
call :makenumber 17
call :makenumber 18
call :makenumber 19
call :makenumber 20
call :makenumber X
rename X.png x.png
call :makenumber T
rename T.png t.png

echo Generating 2/c symbols
convert numbers\2.png -resize 135x135^! numbers\2h.png
call :overlay colorless numbers\2h 2h
call :overlay 2h whiteHalf 2w
call :overlay 2h blueHalf 2u
call :overlay 2h blackHalf 2b
call :overlay 2h redHalf 2r
call :overlay 2h greenHalf 2g
del /F 2h.png
del /F /Q *Half.png
del colorless.png

echo Generating untap symbol
convert -background transparent -fill white -font Times-New-Roman-Bold -size 200x200 -gravity center -kerning -20 label:Q numbers\Q.png
convert numbers\Q.png -roll +2-5 numbers\Q.png
call :overlay untapCircle numbers\Q q
del /F /Q *Circle.png
rmdir /S /Q numbers

echo Generating small color indicators
for %%f in (*Indicator.png) do convert %%~f -resize 11x11^! %%~f
move *Indicator.png ..\..\src\org\rnd\jmagic\gui\icons\

echo Generating small icons
mkdir big
move * big
for %%f in (big\*.png) do convert %%~f -resize 12x12^! %%~nxf
mkdir little
for %%f in (big\*.png) do convert %%~f -resize 9x9^! little\%%~nxf
rmdir /S /Q big

popd

echo Moving icons to project
move icons\*.png ..\..\src\org\rnd\jmagic\gui\icons\
move icons\little\*.png ..\..\src\org\rnd\jmagic\gui\icons\little\
rmdir icons\little

rem ==========================================================================
echo Generating flip icon

convert -size 1001x1001 xc:black ^
	-fill white -draw "circle 500,500 500,1000" ^
	-fill black -draw "circle 500,500 500,750" ^
	-fill white -draw "polygon 1000,0 1000,500 0,500 0,1000" ^
	-fill black -draw "polygon 1000,750 1000,500 0,500 0,250" ^
	arrowsMask.png

convert -size 1001x1001 xc:black arrowsMask.png ^
	-alpha Off -compose Copy_Opacity -composite ^
	arrows.png

rem edge size: 75 pixels
rem triangle calculations:
rem	75(1 + sqrt(2)) = 181
rem	50/sqrt(5) = 33

convert -size 1001x1001 xc:black ^
	-fill white -draw "circle 500,500 500,925" ^
	-fill black ^
		-draw "circle 500,500 500,825" ^
		-draw "rectangle 0,425 1000,575" ^
		-draw "polygon 33,184 533,434 0,500" ^
		-draw "polygon 967,816 467,566 1000,500" ^
	-fill white ^
		-draw "polygon 925,425 925,181 681,425" ^
		-draw "polygon 75,575 75,819 319,575" ^
	arrowsMask.png

convert -size 1001x1001 xc:white arrowsMask.png ^
	-alpha Off -compose Copy_Opacity -composite ^
	arrowsSmaller.png

convert arrows.png arrowsSmaller.png -composite flip.png
convert flip.png -resize 14x14^! flip.png

move flip.png ..\..\src\org\rnd\jmagic\gui\icons\
del /F /Q arrows.png arrowsMask.png arrowsSmaller.png

rem ==========================================================================
echo Generating back_face icon

convert -size 1001x1001 xc:black ^
	-fill white -draw "circle 2000,-1000 0,0" ^
	page.png

convert -size 1001x1001 xc:white ^
	-fill black ^
		-draw "circle 500,-1000 1000,0" ^
		-draw "circle 2000,500 1000,0" ^
	mask.png

convert -size 1001x1001 xc:transparent ^
	-fill black ^
		-draw "circle 500,-1000 1044,88" ^
		-draw "circle 2000,500 912,-44" ^
	edges.png

convert page.png edges.png -composite back_face.png

call :mask back_face mask back_face
convert back_face.png -resize 14x14^! back_face.png

move back_face.png ..\..\src\org\rnd\jmagic\gui\icons\
del /F /Q mask.png page.png edges.png

rem ==========================================================================
echo Generating physical icon

convert -size 14x14 xc:transparent ^
	-fill black -draw "circle 6.5,6.5 6.5,0" ^
	-fill white -draw "circle 6.5,6.5 6.5,2" ^
	..\..\src\org\rnd\jmagic\gui\icons\physical.png

goto :eof

rem ======================================================
rem Generates %2.png by masking %1.png with circleMask.png
rem ======================================================
:makeCircle
call :mask %1 circleMask %2
goto :eof


rem ===================================================================
rem Generates %3.png by using %2.png as a transparency mask on %1.png.
rem ===================================================================
:mask
convert %1.png %2.png -alpha Off -compose Copy_Opacity -composite %3.png
goto :eof

rem ========================================================
rem Generates %3.png by overlaying %2.png on top of %1.png.
rem ========================================================
:overlay
convert %1.png %2.png -composite %3.png
goto :eof

:makenumber
convert -background transparent -fill black -font Times-New-Roman-Bold -size 200x200 -gravity center -kerning -20 "label:%1" numbers\%1.png
convert numbers\%1.png -roll +2-5 numbers\%1.png
call :overlay colorless numbers\%1 %1
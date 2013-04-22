@echo off
rem Generates a bunch of card frames based on frame images for w, u, b, r, g,
rem a, m, and l cards.  Expects ImageMagick to be installed, and expects the
rem base frames to reside in "frames", and expects frames\resources\_boxes.PNG and
rem frames\resources\_pinline.PNG to represent masks.  Will overwrite files without asking.

pushd frames

mkdir largeframes
copy frame_a.png largeframes\frame_a.png
copy frame_b.png largeframes\frame_b.png
copy frame_l.png largeframes\frame_l.png
copy frame_m.png largeframes\frame_m.png
copy frame_w.png largeframes\frame_w.png
copy frame_u.png largeframes\frame_u.png
copy frame_g.png largeframes\frame_g.png
copy frame_r.png largeframes\frame_r.png
copy frame_ability.png largeframes\frame_ability.png
copy back.png largeframes\back.png
pushd largeframes

echo Generating fade template
convert xc:black xc:white +append -resize 223x310^! ..\resources\_fade.png
mkdir _intermediate

echo Generating half-frames
call :mask frame_w ..\resources\_fade _intermediate\frame_w_right
call :mask frame_u ..\resources\_fade _intermediate\frame_u_right
call :mask frame_b ..\resources\_fade _intermediate\frame_b_right
call :mask frame_r ..\resources\_fade _intermediate\frame_r_right
call :mask frame_g ..\resources\_fade _intermediate\frame_g_right

echo Generating hybrid frames
call :overlay frame_w _intermediate\frame_u_right _intermediate\frame_wu
call :overlay frame_w _intermediate\frame_b_right _intermediate\frame_wb
call :overlay frame_u _intermediate\frame_b_right _intermediate\frame_ub
call :overlay frame_u _intermediate\frame_r_right _intermediate\frame_ur
call :overlay frame_b _intermediate\frame_r_right _intermediate\frame_br
call :overlay frame_b _intermediate\frame_g_right _intermediate\frame_bg
call :overlay frame_r _intermediate\frame_g_right _intermediate\frame_rg
call :overlay frame_r _intermediate\frame_w_right _intermediate\frame_rw
call :overlay frame_g _intermediate\frame_w_right _intermediate\frame_gw
call :overlay frame_g _intermediate\frame_u_right _intermediate\frame_gu

echo Generating pinlines
call :mask frame_w ..\resources\_pinline _intermediate\pinline_w
call :mask frame_u ..\resources\_pinline _intermediate\pinline_u
call :mask frame_b ..\resources\_pinline _intermediate\pinline_b
call :mask frame_r ..\resources\_pinline _intermediate\pinline_r
call :mask frame_g ..\resources\_pinline _intermediate\pinline_g
call :mask _intermediate\frame_wu ..\resources\_pinline _intermediate\pinline_wu
call :mask _intermediate\frame_wb ..\resources\_pinline _intermediate\pinline_wb
call :mask _intermediate\frame_ub ..\resources\_pinline _intermediate\pinline_ub
call :mask _intermediate\frame_ur ..\resources\_pinline _intermediate\pinline_ur
call :mask _intermediate\frame_br ..\resources\_pinline _intermediate\pinline_br
call :mask _intermediate\frame_bg ..\resources\_pinline _intermediate\pinline_bg
call :mask _intermediate\frame_rg ..\resources\_pinline _intermediate\pinline_rg
call :mask _intermediate\frame_rw ..\resources\_pinline _intermediate\pinline_rw
call :mask _intermediate\frame_gw ..\resources\_pinline _intermediate\pinline_gw
call :mask _intermediate\frame_gu ..\resources\_pinline _intermediate\pinline_gu
call :mask frame_m ..\resources\_pinline _intermediate\pinline_m

echo Generating gold frames
call :overlay frame_m _intermediate\pinline_wu frame_mwu
call :overlay frame_m _intermediate\pinline_ub frame_mub
call :overlay frame_m _intermediate\pinline_br frame_mbr
call :overlay frame_m _intermediate\pinline_rg frame_mrg
call :overlay frame_m _intermediate\pinline_gw frame_mgw
call :overlay frame_m _intermediate\pinline_wb frame_mwb
call :overlay frame_m _intermediate\pinline_ur frame_mur
call :overlay frame_m _intermediate\pinline_bg frame_mbg
call :overlay frame_m _intermediate\pinline_rw frame_mrw
call :overlay frame_m _intermediate\pinline_gu frame_mgu

echo Generating hybrid text boxes
call :mask frame_l ..\resources\_boxes _intermediate\boxes_misc
call :overlay _intermediate\frame_wu _intermediate\boxes_misc frame_wu
call :overlay _intermediate\frame_ub _intermediate\boxes_misc frame_ub
call :overlay _intermediate\frame_br _intermediate\boxes_misc frame_br
call :overlay _intermediate\frame_rg _intermediate\boxes_misc frame_rg
call :overlay _intermediate\frame_gw _intermediate\boxes_misc frame_gw
call :overlay _intermediate\frame_wb _intermediate\boxes_misc frame_wb
call :overlay _intermediate\frame_bg _intermediate\boxes_misc frame_bg
call :overlay _intermediate\frame_gu _intermediate\boxes_misc frame_gu
call :overlay _intermediate\frame_ur _intermediate\boxes_misc frame_ur
call :overlay _intermediate\frame_rw _intermediate\boxes_misc frame_rw

echo Generating other text boxes
call :mask frame_w ..\resources\_boxes _intermediate\boxes_w
call :mask frame_u ..\resources\_boxes _intermediate\boxes_u
call :mask frame_b ..\resources\_boxes _intermediate\boxes_b
call :mask frame_r ..\resources\_boxes _intermediate\boxes_r
call :mask frame_g ..\resources\_boxes _intermediate\boxes_g
call :mask frame_m ..\resources\_boxes _intermediate\boxes_m

echo Generating land one-color frames
call :overlay frame_l _intermediate\pinline_w _intermediate\frame_lw
call :overlay _intermediate\frame_lw _intermediate\boxes_w frame_lw
call :overlay frame_l _intermediate\pinline_u _intermediate\frame_lu
call :overlay _intermediate\frame_lu _intermediate\boxes_u frame_lu
call :overlay frame_l _intermediate\pinline_b _intermediate\frame_lb
call :overlay _intermediate\frame_lb _intermediate\boxes_b frame_lb
call :overlay frame_l _intermediate\pinline_r _intermediate\frame_lr
call :overlay _intermediate\frame_lr _intermediate\boxes_r frame_lr
call :overlay frame_l _intermediate\pinline_g _intermediate\frame_lg
call :overlay _intermediate\frame_lg _intermediate\boxes_g frame_lg

echo Generating land two-plus-color frames
call :overlay frame_l _intermediate\pinline_wu frame_lwu
call :overlay frame_l _intermediate\pinline_ub frame_lub
call :overlay frame_l _intermediate\pinline_br frame_lbr
call :overlay frame_l _intermediate\pinline_rg frame_lrg
call :overlay frame_l _intermediate\pinline_gw frame_lgw
call :overlay frame_l _intermediate\pinline_wb frame_lwb
call :overlay frame_l _intermediate\pinline_ur frame_lur
call :overlay frame_l _intermediate\pinline_bg frame_lbg
call :overlay frame_l _intermediate\pinline_rw frame_lrw
call :overlay frame_l _intermediate\pinline_gu frame_lgu

call :overlay frame_l _intermediate\pinline_m _intermediate\frame_lm
call :overlay _intermediate\frame_lm _intermediate\boxes_m frame_lm

echo Generating artifact one-color frames
call :overlay frame_a _intermediate\pinline_w _intermediate\frame_aw
call :overlay _intermediate\frame_aw _intermediate\boxes_w frame_aw
call :overlay frame_a _intermediate\pinline_u _intermediate\frame_au
call :overlay _intermediate\frame_au _intermediate\boxes_u frame_au
call :overlay frame_a _intermediate\pinline_b _intermediate\frame_ab
call :overlay _intermediate\frame_ab _intermediate\boxes_b frame_ab
call :overlay frame_a _intermediate\pinline_r _intermediate\frame_ar
call :overlay _intermediate\frame_ar _intermediate\boxes_r frame_ar
call :overlay frame_a _intermediate\pinline_g _intermediate\frame_ag
call :overlay _intermediate\frame_ag _intermediate\boxes_g frame_ag
call :overlay frame_a _intermediate\pinline_m _intermediate\frame_am
call :overlay _intermediate\frame_am _intermediate\boxes_m frame_am

echo Generating artifact hybrid frames
call :overlay frame_a _intermediate\pinline_wu frame_awu
call :overlay frame_a _intermediate\pinline_ub frame_aub
call :overlay frame_a _intermediate\pinline_br frame_abr
call :overlay frame_a _intermediate\pinline_rg frame_arg
call :overlay frame_a _intermediate\pinline_gw frame_agw
call :overlay frame_a _intermediate\pinline_wb frame_awb
call :overlay frame_a _intermediate\pinline_ur frame_aur
call :overlay frame_a _intermediate\pinline_bg frame_abg
call :overlay frame_a _intermediate\pinline_rw frame_arw
call :overlay frame_a _intermediate\pinline_gu frame_agu

echo Generating artifact gold frames
call :overlay frame_am _intermediate\pinline_wu frame_amwu
call :overlay frame_am _intermediate\pinline_ub frame_amub
call :overlay frame_am _intermediate\pinline_br frame_ambr
call :overlay frame_am _intermediate\pinline_rg frame_amrg
call :overlay frame_am _intermediate\pinline_gw frame_amgw
call :overlay frame_am _intermediate\pinline_wb frame_amwb
call :overlay frame_am _intermediate\pinline_ur frame_amur
call :overlay frame_am _intermediate\pinline_bg frame_ambg
call :overlay frame_am _intermediate\pinline_rw frame_amrw
call :overlay frame_am _intermediate\pinline_gu frame_amgu

echo Generating small power/toughness boxes
call :ptbox frame_w ptbox_w 83 40
call :ptbox frame_u ptbox_u 83 40
call :ptbox frame_b ptbox_b 83 40
call :ptbox frame_r ptbox_r 83 40
call :ptbox frame_g ptbox_g 83 40
call :ptbox frame_a ptbox_a 83 40
call :ptbox frame_m ptbox_m 83 40
call :ptbox _intermediate\boxes_misc ptbox_misc 83 40
convert ptbox_misc.png ^
	-fill black -draw "color 10,10 floodfill" ^
	-fill gray -draw "color 1,1 floodfill" ^
	loyaltybox.png
convert loyaltybox.png ^
	-stroke white ^
		-draw "line 3,3 79,3" ^
		-draw "line 79,3 79,36" ^
	loyaltybox.png
popd

echo Generating small frames
mkdir smallframes
for %%f in (largeframes\*.png) do convert %%~f -resize 42%% smallframes\%%~nxf

echo Generating power/toughness boxes
cd largeframes
call :ptbox frame_w ptbox_w 39 21
call :ptbox frame_u ptbox_u 39 21
call :ptbox frame_b ptbox_b 39 21
call :ptbox frame_r ptbox_r 39 21
call :ptbox frame_g ptbox_g 39 21
call :ptbox frame_a ptbox_a 39 21
call :ptbox frame_m ptbox_m 39 21
call :ptbox _intermediate\boxes_misc ptbox_misc 39 21
convert ptbox_misc.png ^
	-fill black -draw "color 10,10 floodfill" ^
	-fill gray -draw "color 1,1 floodfill" ^
	loyaltybox.png
convert loyaltybox.png ^
	-stroke white ^
		-draw "line 3,3 35,3" ^
		-draw "line 35,3 35,17" ^
	loyaltybox.png
popd

echo Moving frames to project
move frames\smallframes\*.png ..\..\src\org\rnd\jmagic\gui\smallframes\
move frames\largeframes\*.png ..\..\src\org\rnd\jmagic\gui\largeframes\

echo Cleaning up resources
del frames\resources\_fade.png
rmdir /S /Q frames\largeframes\_intermediate
rmdir frames\largeframes
rmdir frames\smallframes

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

rem ==================================================================================
rem Draws a p/t box using the color at 35,25 on %1.png and puts the result at %2.png.
rem The size of the p/t box will be %3 x %4.
rem ==================================================================================
:ptbox
set /a RIGHT=%3-1
set /a BOTTOM=%4-1
set /a HIGHLIGHTRIGHT=%3-4
set /a HIGHLIGHTBOTTOM=%4-4
convert %1.png -crop 1x1+35+25 -resize %3x%4^! ^
 	-stroke white ^
		-draw "line 0,0 %RIGHT%,0" ^
		-draw "line %RIGHT%,0 %RIGHT%,20" ^
		-draw "line 3,4 3,%HIGHLIGHTBOTTOM%" ^
		-draw "line 3,%HIGHLIGHTBOTTOM% %HIGHLIGHTRIGHT%,%HIGHLIGHTBOTTOM%" ^
	-stroke black ^
		-draw "line 0,1 0,%BOTTOM%" ^
		-draw "line 0,%BOTTOM% %RIGHT%,%BOTTOM%" ^
		-draw "line 3,3 %HIGHLIGHTRIGHT%,3" ^
		-draw "line %HIGHLIGHTRIGHT%,3 %HIGHLIGHTRIGHT%,%HIGHLIGHTBOTTOM%" ^
	%2.png
goto :eof
package org.rnd.jmagic.gui;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class CardGraphics extends org.rnd.util.Graphics2DAdapter
{
	public static final java.awt.Dimension COLOR_INDICATOR = new java.awt.Dimension(11, 11);

	/**
	 * This serves as a sentinel value that a key in the image cache will never
	 * have an image associated with it.
	 */
	private static final java.awt.Image IMAGE_CACHE_NO_IMAGE = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_RGB);

	/**
	 * This serves as a sentinel value that an attempt is being made to retrieve
	 * an image for the image cache.
	 */
	private static final java.awt.Image IMAGE_CACHE_WORKING = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_RGB);

	public static final java.awt.Dimension LARGE_CARD = new java.awt.Dimension(223, 310);

	private static final int LARGE_CARD_ART_LEFT = 20;

	private static final int LARGE_CARD_ART_TOP = 36;

	private static final int LARGE_CARD_ART_WIDTH = 183;

	private static final int LARGE_CARD_ART_HEIGHT = 135;

	private static final int LARGE_CARD_LIFE_RIGHT = 19;

	public static final int LARGE_CARD_MANA_TOP = 19;

	public static final int LARGE_CARD_NAME_TOP = 18;

	public static final int LARGE_CARD_PADDING_LEFT = 22;

	public static final int LARGE_CARD_PADDING_RIGHT = 22;

	private static final int LARGE_CARD_POISON_BOTTOM = 17;

	private static final int LARGE_CARD_PT_BOX_LEFT = 170;

	private static final int LARGE_CARD_PT_BOX_TOP = 275;

	private static final int LARGE_CARD_PT_TEXT_LEFT = 174;

	private static final int LARGE_CARD_PT_TEXT_TOP = 278;

	private static final java.awt.Dimension LARGE_CARD_PT_DIMENSIONS = new java.awt.Dimension(32, 14);

	public static final int LARGE_CARD_TEXT_BOX_HEIGHT = 82;

	public static final int LARGE_CARD_TEXT_BOX_TOP = 193;

	public static final int LARGE_CARD_TEXT_LINE_HEIGHT = 14;

	public static final int LARGE_CARD_TEXT_WIDTH = LARGE_CARD.width - LARGE_CARD_PADDING_LEFT - LARGE_CARD_PADDING_RIGHT;

	public static final int LARGE_CARD_TYPE_TOP = 175;

	public static final int LARGE_CARD_TOTAL_TEXT_HEIGHT = LARGE_CARD_PT_TEXT_TOP - LARGE_CARD_NAME_TOP;

	public static final java.awt.Dimension LARGE_MANA_SYMBOL = new java.awt.Dimension(12, 12);

	public static final java.awt.Color LIFE_TOTAL_COLOR = java.awt.Color.GREEN.darker().darker();

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(CardGraphics.class.getName());

	private static final java.awt.Color POISON_COUNTER_COLOR = java.awt.Color.RED.darker();

	private static final int POISON_LIFE_PADDING = 1;

	public static final java.awt.Dimension SMALL_CARD = new java.awt.Dimension(94, 130);

	private static final int SMALL_CARD_ART_LEFT = 8;

	private static final int SMALL_CARD_ART_TOP = 16;

	private static final int SMALL_CARD_ART_WIDTH = 77;

	private static final int SMALL_CARD_ART_HEIGHT = 56;

	private static final int SMALL_CARD_DAMAGE_BOTTOM = 27;

	private static final int SMALL_CARD_DAMAGE_RIGHT = 13;

	public static final java.awt.Dimension SMALL_CARD_PADDING = new java.awt.Dimension(5, 40);

	public static final int SMALL_CARD_PADDING_LEFT = 10;

	public static final int SMALL_CARD_PADDING_RIGHT = 11;

	public static final int SMALL_CARD_PADDING_TOP = 17;

	public static final int SMALL_CARD_ICON_HEIGHT = 14;

	public static final int SMALL_CARD_ICON_WIDTH = 14;

	public static final int SMALL_CARD_ICON_PADDING = 1;

	private static final int SMALL_CARD_POISON_BOTTOM = 12;

	private static final int SMALL_CARD_PT_BOX_LEFT = 53;

	private static final int SMALL_CARD_PT_BOX_TOP = 106;

	private static final int SMALL_CARD_PT_TEXT_LEFT = 55;

	private static final int SMALL_CARD_PT_TEXT_TOP = 107;

	// The inside of the small PT box is the same size as the inside of the
	// large PT box
	private static final java.awt.Dimension SMALL_CARD_PT_DIMENSIONS = LARGE_CARD_PT_DIMENSIONS;

	public static final int SMALL_CARD_TEXT_WIDTH = SMALL_CARD.width - SMALL_CARD_PADDING_LEFT - SMALL_CARD_PADDING_RIGHT;

	public static final int SMALL_CARD_TOTAL_TEXT_HEIGHT = 108;

	public static final java.awt.Dimension SMALL_MANA_SYMBOL = new java.awt.Dimension(9, 9);

	private static final java.util.concurrent.ConcurrentMap<String, java.awt.Image> imageCache = new java.util.concurrent.ConcurrentHashMap<String, java.awt.Image>();

	public static SanitizedGameObject.CharacteristicSet getLargeCardDisplayOption(java.awt.event.MouseEvent e, java.awt.Point smallCardStart, SanitizedGameObject hoveredCard, boolean flipped)
	{
		int options = 0;
		for(SanitizedGameObject.CharacteristicSet option: CardGraphics.getLargeCardDisplayOptions(hoveredCard))
		{
			++options;
			if(getSmallCardOptionRect(hoveredCard.tapped, flipped, smallCardStart, options).contains(e.getPoint()))
				return option;
		}
		return SanitizedGameObject.CharacteristicSet.ACTUAL;
	}

	public static int getCharacteristicIndex(java.awt.event.MouseEvent e, java.awt.Point cardStart, SanitizedGameObject display)
	{
		if(display.characteristics.length == 1)
			return 0;
		for(int i = 0; i < display.characteristics.length; ++i)
			if(getSmallCardCharacteristicRect(cardStart, i, display.characteristics.length).contains(e.getPoint()))
				return i;
		return 0;
	}

	private static java.util.Set<SanitizedGameObject.CharacteristicSet> getLargeCardDisplayOptions(SanitizedGameObject object)
	{
		java.util.Set<SanitizedGameObject.CharacteristicSet> ret = java.util.EnumSet.noneOf(SanitizedGameObject.CharacteristicSet.class);
		if(object.characteristics[0].containsKey(SanitizedGameObject.CharacteristicSet.PHYSICAL))
			ret.add(SanitizedGameObject.CharacteristicSet.PHYSICAL);
		if(!object.flipped && object.characteristics[0].containsKey(SanitizedGameObject.CharacteristicSet.FLIP))
			ret.add(SanitizedGameObject.CharacteristicSet.FLIP);
		if(!object.transformed && object.characteristics[0].containsKey(SanitizedGameObject.CharacteristicSet.BACK_FACE))
			ret.add(SanitizedGameObject.CharacteristicSet.BACK_FACE);
		return ret;
	}

	private static java.awt.Rectangle getSmallCardOptionRect(boolean tapped, boolean flipped, java.awt.Point smallCardStart, int optionsSoFar)
	{
		int x, y;
		if(flipped && tapped)
		{
			x = (int)(smallCardStart.getX()) + SMALL_CARD.height - ((SMALL_CARD_ICON_HEIGHT + SMALL_CARD_ICON_PADDING) * optionsSoFar) + SMALL_CARD_ICON_PADDING;
			y = (int)(smallCardStart.getY()) + SMALL_CARD.width - SMALL_CARD_ICON_WIDTH;
		}
		else if(flipped)
		{
			x = (int)(smallCardStart.getX()) + SMALL_CARD.width - SMALL_CARD_ICON_WIDTH;
			y = (int)(smallCardStart.getY()) + (SMALL_CARD_ICON_HEIGHT + SMALL_CARD_ICON_PADDING) * (optionsSoFar - 1);
		}
		else if(tapped)
		{
			x = (int)(smallCardStart.getX()) + (SMALL_CARD_ICON_HEIGHT + SMALL_CARD_ICON_PADDING) * (optionsSoFar - 1);
			y = (int)(smallCardStart.getY());
		}
		else
		{
			x = (int)(smallCardStart.getX());
			y = (int)(smallCardStart.getY()) + SMALL_CARD.height - (SMALL_CARD_ICON_HEIGHT + SMALL_CARD_ICON_PADDING) * optionsSoFar + SMALL_CARD_ICON_PADDING;
		}
		return new java.awt.Rectangle(x, y, SMALL_CARD_ICON_WIDTH, SMALL_CARD_ICON_HEIGHT);
	}

	private static java.awt.Rectangle getSmallCardCharacteristicRect(java.awt.Point cardStart, int index, int total)
	{
		int y = (int)(cardStart.getY() + SMALL_CARD.height - (SMALL_CARD.height * (index + 1) / (double)total));
		return new java.awt.Rectangle(cardStart.x, y, SMALL_CARD.width, (int)(SMALL_CARD.height / (double)total));
	}

	private static java.nio.file.Path cardArts = null;

	public static java.nio.file.Path getCardImageLocation()
	{
		return cardArts;
	}

	public static void setCardImageLocation(String location)
	{
		setCardImageLocation(java.nio.file.FileSystems.getDefault().getPath(location));
	}

	public static void setCardImageLocation(java.nio.file.Path location)
	{
		if(java.nio.file.Files.isDirectory(location) && java.nio.file.Files.isWritable(location))
			cardArts = location;
		else
			cardArts = null;
	}

	public static java.text.AttributedString getAttributedString(String text, java.awt.FontMetrics font, boolean replaceIcons)
	{
		java.util.Map<Integer, java.awt.font.ImageGraphicAttribute> charReplacements = new java.util.HashMap<Integer, java.awt.font.ImageGraphicAttribute>();

		if(replaceIcons)
			while(text.contains("("))
			{
				int index = text.indexOf('(');
				String replace = text.substring(index, text.indexOf(')') + 1);
				text = text.substring(0, index) + "X" + text.substring(index + replace.length());
				charReplacements.put(index, new java.awt.font.ImageGraphicAttribute(getIcon(replace, true), java.awt.font.GraphicAttribute.CENTER_BASELINE, 0f, CardGraphics.SMALL_MANA_SYMBOL.height / 2f));
			}

		java.text.AttributedString attrText = new java.text.AttributedString(text);

		attrText.addAttribute(java.awt.font.TextAttribute.FONT, font.getFont());

		if(replaceIcons)
			for(java.util.Map.Entry<Integer, java.awt.font.ImageGraphicAttribute> replacement: charReplacements.entrySet())
			{
				int index = replacement.getKey();
				attrText.addAttribute(java.awt.font.TextAttribute.CHAR_REPLACEMENT, replacement.getValue(), index, replacement.getKey() + 1);
			}

		return attrText;
	}

	private static String getCardFrameString(SanitizedIdentified i, SanitizedGameObject.CharacteristicSet option, int characteristicIndex)
	{
		if(null == i)
			return "back.png";
		if(i instanceof SanitizedGameObject && ((SanitizedGameObject)i).faceDown)
			return "back.png";

		if(i instanceof SanitizedPlayer)
			return "frame_ability.png";

		if(!(i instanceof SanitizedGameObject))
			return "frame_ability.png";

		SanitizedGameObject o = (SanitizedGameObject)i;
		if(o instanceof SanitizedNonStaticAbility || o.isEmblem)
			return "frame_ability.png";

		java.util.Set<Color> colors = null;

		SanitizedCharacteristics c = o.characteristics[characteristicIndex].get(option);

		if(c.types.contains(Type.LAND))
			colors = o.canProduce;
		else
			colors = c.colors;

		String colorString = getColorString(colors, false);

		StringBuffer baseFrame = new StringBuffer();
		if(c.types.contains(Type.LAND))
			baseFrame.append("l");
		else if(c.types.contains(Type.ARTIFACT))
			baseFrame.append("a");
		else if(colors.size() == 0)
			baseFrame.append("a");

		if(colors.size() > 2)
			baseFrame.append("m");
		else if(colors.size() > 1 && c.manaCost != null)
			for(ManaSymbol m: c.manaCost)
				if(m.colors.size() == 1)
				{
					baseFrame.append("m");
					break;
				}
		return "frame_" + baseFrame + colorString + ".png";
	}

	/**
	 * Returns a string representing the given colors. One lowercase letter per
	 * color, in the "right" order (i.e., wu, gw). If there are no colors, three
	 * colors, or four colors, returns the empty string. If fiveColor is false,
	 * also returns the empty string for five colors.
	 */
	private static String getColorString(java.util.Set<Color> colors, boolean fiveColor)
	{
		if(colors.size() == 0 || colors.size() == 3 || colors.size() == 4)
			return "";
		if(colors.size() == 1)
			return colors.iterator().next().getLetter().toLowerCase();
		else if(colors.size() == 2)
		{
			boolean w = colors.contains(Color.WHITE);
			boolean u = colors.contains(Color.BLUE);
			boolean b = colors.contains(Color.BLACK);
			boolean r = colors.contains(Color.RED);
			boolean g = colors.contains(Color.GREEN);

			if(w && u)
				return "wu";
			else if(u && b)
				return "ub";
			else if(b && r)
				return "br";
			else if(r && g)
				return "rg";
			else if(g && w)
				return "gw";
			else if(w && b)
				return "wb";
			else if(u && r)
				return "ur";
			else if(b && g)
				return "bg";
			else if(r && w)
				return "rw";
			else if(g && u)
				return "gu";
		}
		return fiveColor ? "wubrg" : "";
	}

	public static java.awt.Image getIcon(String iconName, boolean little)
	{
		java.awt.Image icon = null;

		String iconSize = "";
		if(little)
			iconSize = "little/";

		if(iconName.equals("(T)"))
		{
			icon = getImage("icons/" + iconSize + "t.png");
		}
		else if(iconName.equals("(Q)"))
		{
			icon = getImage("icons/" + iconSize + "q.png");
		}
		else if(iconName.equals("(C)"))
		{
			icon = getImage("icons/" + iconSize + "c.png");
		}
		// this has toUpperCase because most of the time gatherer's phyrexian
		// symbols have lowercase letters.
		else if(iconName.toUpperCase().equals("(P)"))
		{
			icon = getImage("icons/" + iconSize + "p.png");
		}
		else if(iconName.equals("(X)"))
		{
			icon = getImage("icons/" + iconSize + "x.png");
		}
		else
		{
			try
			{
				icon = getImage("icons/" + iconSize + getManaSymbolString(new ManaPool(iconName).iterator().next()) + ".png");
			}
			catch(java.util.NoSuchElementException e)
			{
				icon = null;
			}
		}

		if(icon == null)
			icon = getImage("noimage.png");

		return icon;
	}

	public static java.awt.Image getImage(String name)
	{
		java.net.URL location = CardGraphics.class.getResource(name);
		if(null == location)
		{
			location = CardGraphics.class.getResource("noimage.png");
			if(null == location)
				throw new RuntimeException("Could not find resource \"noimage.png\"");
		}

		if(!imageCache.containsKey(name))
		{
			try
			{
				imageCache.put(name, javax.imageio.ImageIO.read(location));
			}
			catch(java.io.IOException e)
			{
				throw new RuntimeException("Could not read image \"" + name + "\"");
			}
		}

		return imageCache.get(name);
	}

	public static java.awt.Image getLargeCard(SanitizedIdentified object, SanitizedGameObject.CharacteristicSet option, SanitizedGameState state, java.awt.Font font, int characteristicIndex)
	{
		java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(LARGE_CARD.width, LARGE_CARD.height, java.awt.image.BufferedImage.TYPE_INT_RGB);

		CardGraphics cg = new CardGraphics(image, state, font);
		cg.drawLargeCard(object, option, characteristicIndex);

		return image;
	}

	private static String getManaSymbolString(ManaSymbol s)
	{
		if(s.isX)
			return "x";

		String manaString = "";

		if(0 < s.colorless)
			manaString += Integer.toString(s.colorless);

		switch(s.colors.size())
		{
		case 0:
			if(0 == s.colorless)
				return "0";
			break;
		case 1:
		case 2:
			manaString += getColorString(s.getColors(), false);
			break;
		case 5:
			manaString = "wubrg";
			break;
		default:
			manaString = "m"; // this should be impossible...
			break;
		}

		if(s.isPhyrexian)
			manaString += "p";

		return manaString;
	}

	public static java.awt.Image getSmallCard(SanitizedIdentified object, SanitizedGameState state, boolean renderDamage, boolean renderCounters, java.awt.Font font)
	{
		java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(SMALL_CARD.width, SMALL_CARD.height, java.awt.image.BufferedImage.TYPE_INT_ARGB);

		CardGraphics cg = new CardGraphics(image, state, font);
		cg.drawSmallCard(object, renderDamage, renderCounters);

		if(object instanceof SanitizedGameObject && ((SanitizedGameObject)object).ghost)
		{
			java.awt.image.BufferedImage alphaImage = new java.awt.image.BufferedImage(SMALL_CARD.width, SMALL_CARD.height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
			CardGraphics alphaCG = new CardGraphics(alphaImage, state, font);

			float[] scales = {1f, 1f, 1f, 0.5f};
			float[] offsets = new float[4];
			java.awt.image.RescaleOp rop = new java.awt.image.RescaleOp(scales, offsets, null);

			alphaCG.drawImage(image, rop, 0, 0);
			return alphaImage;
		}

		return image;
	}

	private static String getTypeString(SanitizedCharacteristics c)
	{
		StringBuilder ret = new StringBuilder();

		// localized to facilitate use of the map that will be in place later
		for(SuperType superType: c.superTypes)
			ret.append(superType + " ");

		for(Type type: c.types)
			ret.append(type + " ");

		java.util.List<Enum<?>> subTypes = new java.util.LinkedList<Enum<?>>();
		boolean allCreatureTypes = false;
		subTypes.addAll(c.subTypes);
		if(SubType.getSubTypesFor(Type.CREATURE, c.subTypes).size() == SubType.getAllCreatureTypes().size())
		{
			allCreatureTypes = true;
			subTypes.removeAll(SubType.getAllCreatureTypes());
		}
		if(subTypes.isEmpty() && !allCreatureTypes)
			return ret.toString().trim();

		ret.append('\u2014');
		if(allCreatureTypes)
			ret.append(" (all creature types)");
		for(Enum<?> subType: subTypes)
			ret.append(" " + subType.toString());
		return ret.toString();
	}

	private static boolean readCardArtIntoImageCache(String imageCacheKey, java.nio.file.Path path)
	{
		if(!java.nio.file.Files.isReadable(path))
			return false;

		try
		{
			java.awt.Image image = javax.imageio.ImageIO.read(java.nio.file.Files.newInputStream(path));

			// Cache both a small version and a large version of the card art to
			// save processor power resizing repeatedly later.
			java.awt.image.BufferedImage large = new java.awt.image.BufferedImage(LARGE_CARD_ART_WIDTH, LARGE_CARD_ART_HEIGHT, java.awt.image.BufferedImage.TYPE_INT_RGB);
			large.getGraphics().drawImage(image, 0, 0, LARGE_CARD_ART_WIDTH, LARGE_CARD_ART_HEIGHT, null);
			imageCache.put(imageCacheKey, large);

			java.awt.image.BufferedImage small = new java.awt.image.BufferedImage(SMALL_CARD_ART_WIDTH, SMALL_CARD_ART_HEIGHT, java.awt.image.BufferedImage.TYPE_INT_RGB);
			small.getGraphics().drawImage(image, 0, 0, SMALL_CARD_ART_WIDTH, SMALL_CARD_ART_HEIGHT, null);
			imageCache.put("*" + imageCacheKey, small);

			return true;
		}
		catch(java.io.IOException e)
		{
			LOG.log(java.util.logging.Level.INFO, "Could not read card art from " + path, e);
		}

		return false;
	}

	public static java.awt.Image renderTextAsLargeCard(String cardText, java.awt.Font font)
	{
		cardText = cardText.substring(0, 1).toUpperCase() + cardText.substring(1);

		java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(LARGE_CARD.width, LARGE_CARD.height, java.awt.image.BufferedImage.TYPE_INT_RGB);

		CardGraphics cg = new CardGraphics(image, null, font);
		cg.drawImage(CardGraphics.getImage("largeframes/frame_ability.png"), 0, 0, null);

		cg.drawCardText(cardText, cg.getFont(), LARGE_CARD_PADDING_LEFT, LARGE_CARD_NAME_TOP, new java.awt.Dimension(LARGE_CARD_TEXT_WIDTH, LARGE_CARD_TOTAL_TEXT_HEIGHT), false, true);

		return image;
	}

	public static java.awt.Image renderTextAsSmallCard(String cardText, java.awt.Font font)
	{
		cardText = cardText.substring(0, 1).toUpperCase() + cardText.substring(1);

		java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(SMALL_CARD.width, SMALL_CARD.height, java.awt.image.BufferedImage.TYPE_INT_RGB);

		CardGraphics g = new CardGraphics(image, null, font);
		g.drawImage(CardGraphics.getImage("smallframes/frame_ability.png"), 0, 0, null);

		int nameX = SMALL_CARD_PADDING_LEFT;
		int nameY = SMALL_CARD_PADDING_TOP;
		g.drawCardText(cardText, g.getFont(), nameX, nameY, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, true);

		return image;
	}

	private SanitizedGameState state;

	private java.util.Stack<java.awt.geom.AffineTransform> transformStack;

	/**
	 * Construct a CardGraphics by casting a {@link java.awt.Graphics} to a
	 * {@link java.awt.Graphics2D} and keeping the result as the delegate.
	 *
	 * @param graphics The {@link java.awt.Graphics} to cast
	 * @throws ClassCastException If <code>graphics</code> is not a
	 * {@link java.awt.Graphics2D} object
	 */
	public CardGraphics(java.awt.Graphics graphics, SanitizedGameState state)
	{
		this((java.awt.Graphics2D)graphics, state);
	}

	public CardGraphics(java.awt.Graphics2D graphics, SanitizedGameState state)
	{
		super(graphics);
		this.state = state;
		this.transformStack = new java.util.Stack<java.awt.geom.AffineTransform>();
	}

	/**
	 * Constructs a new CardGraphics using the Graphics object from a buffered
	 * image, then sets the font of this object to the default font of the
	 * look-and-feel. This method exists because, since BufferedImages' graphics
	 * aren't usually used to draw text, they don't specify their fonts
	 * "correctly".
	 *
	 * @param image The image whose graphics to create this CardGraphics from.
	 */
	public CardGraphics(java.awt.image.BufferedImage image, SanitizedGameState state, java.awt.Font font)
	{
		this(image.createGraphics(), state);
		this.setFont(font);
	}

	@Override
	public CardGraphics create()
	{
		return new CardGraphics(this, this.state);
	}

	public void drawArrow(java.awt.Point source, java.awt.Point target, java.awt.Color color, boolean borderOnly)
	{
		int dx = target.x - source.x;
		int dy = target.y - source.y;
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		double angle = Math.atan(dy / (double)dx);
		if(dx < 0)
			angle += Math.PI;

		int width = 12;
		int headExtension = 10;
		int padding = 5;

		int headLength = width / 2 + headExtension;

		// If the arrow is less than twice the length of an arrowhead, shrink
		// everything so it's twice the length of an arrowhead.
		int arrowLength = 2 * padding + 2 * headLength;
		if(arrowLength > distance)
		{
			double factor = distance / arrowLength;

			width *= factor;
			headExtension *= factor;
			padding *= factor;
			headLength = width / 2 + headExtension;
		}

		java.awt.Polygon arrow = new java.awt.Polygon();

		arrow.addPoint(padding + headLength, width / 2);
		arrow.addPoint((int)distance - headLength - padding, width / 2);
		arrow.addPoint((int)distance - headLength - padding, width / 2 + headExtension);
		arrow.addPoint((int)distance - padding, 0);
		arrow.addPoint((int)distance - headLength - padding, -width / 2 - headExtension);
		arrow.addPoint((int)distance - headLength - padding, -width / 2);
		arrow.addPoint(padding + headLength, -width / 2);

		this.pushTransform();
		this.translate(source.x, source.y);
		this.rotate(angle);

		this.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), 128));
		if(borderOnly)
			this.drawPolygon(arrow);
		else
			this.fillPolygon(arrow);

		this.popTransform();
	}

	/**
	 * Draws text.
	 *
	 * @param text The text to draw.
	 * @param x The leftmost pixel of text.
	 * @param y The top of the text.
	 * @param color
	 */
	private void drawCardText(java.awt.font.TextLayout text, int x, int y, java.awt.Color color)
	{
		java.awt.Color oldColor = this.getColor();
		this.setColor(color);
		text.draw(this, x, y + this.getFontMetrics().getAscent());
		this.setColor(oldColor);
	}

	/**
	 * Draws black card text to fit within a specific boundary. The text is
	 * drawn at the default size if it will fit; otherwise, the text is shrunk
	 * by half-point increments until it fits.
	 *
	 * @param text The text to draw.
	 * @param font The font with which to render the text.
	 * @param x The leftmost pixel of text.
	 * @param y The top of the text.
	 * @param bound The boundary within which the text is to be drawn.
	 * @param centered Whether to horizontally and vertically center the text
	 * within the boundary.
	 * @param replaceIcons Whether to replace icons in the text (e.g., "(T)" or
	 * "(1)" or "(R)") with graphics.
	 */
	public void drawCardText(String text, java.awt.Font font, int x, int y, java.awt.Dimension bound, boolean centered, boolean replaceIcons)
	{
		this.drawCardText(text, font, x, y, bound, centered, replaceIcons, java.awt.Color.BLACK);
	}

	/**
	 * Draws card text to fit within a specific boundary. The text is drawn at
	 * the default size if it will fit; otherwise, the text is shrunk by
	 * half-point increments until it fits.
	 *
	 * @param text The text to draw.
	 * @param font The font with which to render the text.
	 * @param x The leftmost pixel of text.
	 * @param y The top of the text.
	 * @param bound The boundary within which the text is to be drawn.
	 * @param centered Whether to horizontally and vertically center the text
	 * within the boundary.
	 * @param replaceIcons Whether to replace icons in the text (e.g., "(T)" or
	 * "(1)" or "(R)") with graphics.
	 * @param color What color to draw the text in.
	 */
	private void drawCardText(String text, java.awt.Font font, int x, int y, java.awt.Dimension bound, boolean centered, boolean replaceIcons, java.awt.Color color)
	{
		java.awt.Font oldFont = this.getFont();
		this.setFont(font);
		java.awt.FontMetrics metrics = this.getFontMetrics();
		int lineHeight = metrics.getHeight();
		java.util.List<String> paragraphs = new java.util.LinkedList<String>();

		while(text.contains("\n"))
		{
			int index = text.indexOf("\n");
			paragraphs.add(text.substring(0, index));
			text = text.substring(index + 1);
		}
		paragraphs.add(text);

		java.util.List<java.awt.font.TextLayout> lines = new java.util.LinkedList<java.awt.font.TextLayout>();

		boolean first = true;
		int height = 0;
		boolean tooBig = false;
		for(String paragraph: paragraphs)
		{
			if(first)
				first = false;
			else
			{
				lines.add(null);
				height += (lineHeight / 2);
			}

			java.text.AttributedCharacterIterator attrIter = CardGraphics.getAttributedString(paragraph, getFontMetrics(), replaceIcons).getIterator();
			java.awt.font.LineBreakMeasurer lbm = new java.awt.font.LineBreakMeasurer(attrIter, getFontRenderContext());

			int endIndex = attrIter.getEndIndex();
			while(lbm.getPosition() != endIndex)
			{
				java.awt.font.TextLayout nextLayout = lbm.nextLayout(bound.width, endIndex, true);
				if(nextLayout == null)
				{
					tooBig = true;
					break;
				}
				height += lineHeight;
				if(height > bound.height)
				{
					tooBig = true;
					break;
				}
				lines.add(nextLayout);
			}
		}

		if(tooBig)
		{
			float newPoint = font.getSize2D() - 0.5f;
			if(newPoint <= 0)
				return;
			this.setFont(font.deriveFont(newPoint));

			this.drawCardText(text, font.deriveFont(newPoint), x, y, bound, centered, replaceIcons, color);
		}
		else
		{
			for(java.awt.font.TextLayout line: lines)
			{
				if(null == line)
				{
					// Treat this as a paragraph break
					y += lineHeight / 2;
				}
				else
				{
					if(centered)
					{
						x += (int)((bound.width - line.getAdvance()) / 2);
						y += (int)((bound.height - line.getAscent() - line.getDescent()) / 2);
					}
					this.drawCardText(line, x, y, color);
					y += lineHeight;
				}
			}
		}
		this.setFont(oldFont);
	}

	/**
	 * Draws a single line of right-aligned text.
	 *
	 * @param text The text to draw.
	 * @param x The x-coordinate of rightmost pixel of text.
	 * @param y The y-coordinate of the top of the text.
	 */
	private void drawCardTextRightAligned(String text, int x, int y, java.awt.Color color)
	{
		int textWidth = this.getFontMetrics().stringWidth(text);
		this.drawCardText(new java.awt.font.TextLayout(CardGraphics.getAttributedString(text, getFontMetrics(), false).getIterator(), getFontRenderContext()), x - textWidth, y, color);
	}

	private void drawLargeCard(SanitizedIdentified i, SanitizedGameObject.CharacteristicSet option, int characteristicIndex)
	{
		String cardFrameString = getCardFrameString(i, option, characteristicIndex);
		java.awt.Color textColor = cardFrameString.equals("back.png") ? java.awt.Color.WHITE : java.awt.Color.BLACK;
		this.drawImage(CardGraphics.getImage("largeframes/" + cardFrameString), 0, 0, null);

		if(null == i)
			return;

		if(i instanceof SanitizedPlayer)
		{
			SanitizedPlayer player = (SanitizedPlayer)i;
			StringBuilder text = new StringBuilder(i.name);

			text.append("\n");
			int cardsInHand = ((SanitizedZone)this.state.get(player.hand)).objects.size();
			text.append(cardsInHand);
			text.append(cardsInHand == 1 ? " card in hand" : " cards in hand");

			text.append("\n");
			int cardsInYard = ((SanitizedZone)this.state.get(player.graveyard)).objects.size();
			text.append(cardsInYard);
			text.append(cardsInYard == 1 ? " card in graveyard" : " cards in graveyard");

			text.append("\n");
			int cardsInLibrary = ((SanitizedZone)this.state.get(player.library)).objects.size();
			text.append(cardsInLibrary);
			text.append(cardsInLibrary == 1 ? " card in library" : " cards in library");

			if(player.nonPoisonCounters.size() > 0)
			{
				java.util.Map<Counter.CounterType, Integer> counterQuantities = new java.util.HashMap<Counter.CounterType, Integer>();
				for(Counter counter: player.nonPoisonCounters)
				{
					if(counterQuantities.containsKey(counter.getType()))
						counterQuantities.put(counter.getType(), counterQuantities.get(counter.getType()) + 1);
					else
						counterQuantities.put(counter.getType(), 1);
				}

				// Use the EnumSet to order the output consistently
				for(java.util.Map.Entry<Counter.CounterType, Integer> counterQuantity: counterQuantities.entrySet())
				{
					Counter.CounterType type = counterQuantity.getKey();
					Integer count = counterQuantity.getValue();
					text.append("\n" + count + " " + type + (count == 1 ? "" : "s"));
				}
			}

			for(int k: player.keywordAbilities)
			{
				text.append("\n");
				text.append(this.state.get(k));
			}

			for(int a: player.nonStaticAbilities)
			{
				text.append("\n");
				text.append(this.state.get(a));
			}

			this.drawCardText(text.toString(), getFont(), LARGE_CARD_PADDING_LEFT, LARGE_CARD_NAME_TOP, new java.awt.Dimension(LARGE_CARD_TEXT_WIDTH, LARGE_CARD_TOTAL_TEXT_HEIGHT), false, true);

			java.awt.FontMetrics f = getFontMetrics();
			int x = LARGE_CARD.width - LARGE_CARD_LIFE_RIGHT;
			int y = LARGE_CARD.height - LARGE_CARD_POISON_BOTTOM - f.getHeight();
			if(player.poisonCounters == 1)
				this.drawCardTextRightAligned("1 poison counter", x, y, POISON_COUNTER_COLOR);
			else if(player.poisonCounters > 1)
				this.drawCardTextRightAligned(Integer.toString(player.poisonCounters) + " poison counters", x, y, POISON_COUNTER_COLOR);
			y = y - POISON_LIFE_PADDING - f.getHeight();
			this.drawCardTextRightAligned(Integer.toString(player.lifeTotal) + " life", x, y, LIFE_TOTAL_COLOR);
			return;
		}

		SanitizedGameObject object = (SanitizedGameObject)i;
		SanitizedCharacteristics c = object.characteristics[characteristicIndex].get(option);
		boolean isAbility = i instanceof SanitizedNonStaticAbility;

		String name;
		java.awt.Dimension nameDimensions;
		if(isAbility)
		{
			// The "short" name of an ability more represents what we want for
			// the name text
			name = ((SanitizedNonStaticAbility)i).shortName;
			// Allow names to wrap into the art box
			nameDimensions = new java.awt.Dimension(LARGE_CARD_TEXT_WIDTH, LARGE_CARD_ART_TOP + LARGE_CARD_ART_HEIGHT);
		}
		else
		{
			name = c.name;
			nameDimensions = new java.awt.Dimension(LARGE_CARD_TEXT_WIDTH, LARGE_CARD_TEXT_LINE_HEIGHT);
		}
		if(0 != name.length())
			this.drawCardText(name, getFont(), LARGE_CARD_PADDING_LEFT, LARGE_CARD_NAME_TOP, nameDimensions, false, false, textColor);

		if(!isAbility && (null != c.manaCost))
		{
			this.pushTransform();
			this.translate(LARGE_CARD.width - LARGE_MANA_SYMBOL.width - LARGE_CARD_PADDING_RIGHT, LARGE_CARD_MANA_TOP);
			for(ManaSymbol s: c.manaCost.getDisplayOrder())
			{
				this.drawImage(CardGraphics.getImage("icons/" + getManaSymbolString(s) + ".png"), 0, 0, null);
				this.translate(0 - LARGE_MANA_SYMBOL.width, 0);
			}
			this.popTransform();
		}

		java.awt.Image art = getCardArt(c.name, true, object);
		if(art != null)
			this.drawImage(art, LARGE_CARD_ART_LEFT, LARGE_CARD_ART_TOP, LARGE_CARD_ART_WIDTH, LARGE_CARD_ART_HEIGHT, null);

		java.awt.Dimension typeBound = new java.awt.Dimension(LARGE_CARD_TEXT_WIDTH, LARGE_CARD_TEXT_LINE_HEIGHT);
		int typeLeft = LARGE_CARD_PADDING_LEFT;
		if(!c.colorIndicator.isEmpty())
		{
			java.awt.Image icon = getImage("icons/" + getColorString(c.colorIndicator, true) + "Indicator.png");
			this.drawImage(icon, LARGE_CARD_PADDING_LEFT, LARGE_CARD_TYPE_TOP + 1, COLOR_INDICATOR.width, COLOR_INDICATOR.height, null);
			int pad = COLOR_INDICATOR.width + 2;
			typeBound.width -= pad;
			typeLeft += pad;
		}

		String typeString = getTypeString(c);
		if(0 != typeString.length())
			this.drawCardText(typeString, getFont(), typeLeft, LARGE_CARD_TYPE_TOP, typeBound, false, false, textColor);

		boolean isCreature = c.types.contains(Type.CREATURE);
		if(isCreature)
		{
			this.drawPowerToughnessBox(c, LARGE_CARD_PT_BOX_LEFT, LARGE_CARD_PT_BOX_TOP, false);
			this.drawCardText(c.power + "/" + c.toughness, getFont(), LARGE_CARD_PT_TEXT_LEFT, LARGE_CARD_PT_TEXT_TOP, LARGE_CARD_PT_DIMENSIONS, true, false);
		}
		if(c.types.contains(Type.PLANESWALKER))
		{
			int offset = isCreature ? 45 : 0;
			this.drawImage(getImage("largeframes/loyaltybox.png"), LARGE_CARD_PT_BOX_LEFT - offset, LARGE_CARD_PT_BOX_TOP, null);
			this.drawCardText(Integer.toString(loyaltyOf(object, option)), getFont(), LARGE_CARD_PT_TEXT_LEFT - offset, LARGE_CARD_PT_TEXT_TOP, LARGE_CARD_PT_DIMENSIONS, true, false, java.awt.Color.WHITE);
		}
	}

	public void drawManaSymbol(ManaSymbol s)
	{
		this.drawImage(CardGraphics.getImage("icons/" + getManaSymbolString(s) + ".png"), 0, 0, null);
	}

	public void drawPowerToughnessBox(SanitizedCharacteristics c, int x, int y, boolean small)
	{
		String imageName = null;

		if(c.colors.size() == 1)
			imageName = "ptbox_" + c.colors.iterator().next().getLetter().toLowerCase();
		else if(c.colors.size() > 1 && c.manaCost != null)
		{
			for(ManaSymbol m: c.manaCost)
				if(m.colors.size() == 1)
				{
					imageName = "ptbox_m";
					break;
				}
			if(imageName == null) // it's hybrid
				imageName = "ptbox_misc";
		}
		// no colors, or multiple colors and no mana cost
		else if(c.types.contains(Type.ARTIFACT))
			imageName = "ptbox_a";
		else
			imageName = "ptbox_misc";

		String directory = small ? "smallframes/" : "largeframes/";
		this.drawImage(getImage(directory + imageName + ".png"), x, y, null);
	}

	private void drawSmallCard(SanitizedIdentified o, boolean renderDamage, boolean renderCounters)
	{
		if(!(o instanceof SanitizedGameObject) || (((SanitizedGameObject)o).characteristics.length == 1))
			this.drawImage(CardGraphics.getImage("smallframes/" + getCardFrameString(o, SanitizedGameObject.CharacteristicSet.ACTUAL, 0)), 0, 0, null);

		if(null == o)
			return;

		// if we have an ability, just print some basic info about it on the
		// small frame
		if(o instanceof SanitizedNonStaticAbility)
		{
			String text = ((SanitizedNonStaticAbility)o).shortName;
			this.drawCardText(text, getFont(), SMALL_CARD_PADDING_LEFT, SMALL_CARD_PADDING_TOP, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, true);
			return;
		}
		if(o instanceof SanitizedGameObject && ((SanitizedGameObject)o).isEmblem)
		{
			this.drawCardText("Emblem", getFont(), SMALL_CARD_PADDING_LEFT, SMALL_CARD_PADDING_TOP, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, true);
			return;
		}
		// for players, print the name and life total
		if(o instanceof SanitizedPlayer)
		{
			SanitizedPlayer player = (SanitizedPlayer)o;
			String text = player.name;

			int nameX = SMALL_CARD_PADDING_LEFT;
			int nameY = SMALL_CARD_PADDING_TOP;
			this.drawCardText(text, getFont(), nameX, nameY, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, false);

			java.awt.FontMetrics f = this.getFontMetrics();
			int x = SMALL_CARD.width - SMALL_CARD_DAMAGE_RIGHT;
			int y = SMALL_CARD.height - SMALL_CARD_POISON_BOTTOM - f.getHeight();
			if(player.poisonCounters > 0)
				this.drawCardTextRightAligned(Integer.toString(player.poisonCounters) + " poison", x, y, POISON_COUNTER_COLOR);
			y = y - POISON_LIFE_PADDING - f.getHeight();
			this.drawCardTextRightAligned(Integer.toString(player.lifeTotal) + " life", x, y, LIFE_TOTAL_COLOR);

			return;
		}

		if(o instanceof SanitizedGameObject)
		{
			SanitizedGameObject object = (SanitizedGameObject)o;

			if(object.characteristics.length > 1)
			{
				for(int i = 0; i < object.characteristics.length; ++i)
				{
					java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(SMALL_CARD.width, SMALL_CARD.height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
					CardGraphics cg = new CardGraphics(image, state, this.getFont());
					SanitizedCharacteristics characteristics = object.characteristics[i].get(SanitizedGameObject.CharacteristicSet.ACTUAL);

					cg.drawImage(CardGraphics.getImage("smallframes/" + getCardFrameString(o, SanitizedGameObject.CharacteristicSet.ACTUAL, i)), 0, 0, null);

					java.awt.Image art = getCardArt(characteristics.name, false, object);
					if(art != null)
						cg.drawImage(art, SMALL_CARD_ART_LEFT, SMALL_CARD_ART_TOP, SMALL_CARD_ART_WIDTH, SMALL_CARD_ART_HEIGHT, null);

					java.awt.geom.AffineTransform affine = new java.awt.geom.AffineTransform();
					double scaleX = (double)SMALL_CARD.height / (SMALL_CARD.width * object.characteristics.length);
					double scaleY = (double)SMALL_CARD.width / SMALL_CARD.height;
					affine.scale(scaleX, scaleY);

					image = new java.awt.image.AffineTransformOp(affine, null).filter(image, null);
					cg = new CardGraphics(image, state, this.getFont());

					if(art == null)
						cg.drawCardText(characteristics.name, getFont(), (int)(SMALL_CARD_PADDING_LEFT * scaleY), (int)(SMALL_CARD_PADDING_TOP * scaleX), new java.awt.Dimension((int)(SMALL_CARD_TEXT_WIDTH * scaleY), (int)(SMALL_CARD_TOTAL_TEXT_HEIGHT * scaleX)), false, false);
					else
					{
						cg.drawCardText(characteristics.name, getFont(), (int)(SMALL_CARD_PADDING_LEFT * scaleY), (int)(SMALL_CARD_PADDING_TOP * scaleX), new java.awt.Dimension((int)(SMALL_CARD_TEXT_WIDTH * scaleY), (int)(SMALL_CARD_TOTAL_TEXT_HEIGHT * scaleX)), false, false);
						cg.drawCardText(characteristics.name, getFont(), (int)(SMALL_CARD_PADDING_LEFT * scaleY) - 1, (int)(SMALL_CARD_PADDING_TOP * scaleX) - 1, new java.awt.Dimension((int)(SMALL_CARD_TEXT_WIDTH * scaleY), (int)(SMALL_CARD_TOTAL_TEXT_HEIGHT * scaleX)), false, false, java.awt.Color.WHITE);
					}

					affine = new java.awt.geom.AffineTransform();
					affine.translate(0, SMALL_CARD.height - (i * SMALL_CARD.height / object.characteristics.length));
					affine.rotate(Math.toRadians(-90));

					this.drawImage(image, affine, null);
				}
			}
			else
			{
				SanitizedCharacteristics characteristics = object.characteristics[0].get(SanitizedGameObject.CharacteristicSet.ACTUAL);

				java.awt.Image art = getCardArt(characteristics.name, false, object);
				if(0 != characteristics.name.length())
				{
					if(art == null)
						this.drawCardText(characteristics.name, getFont(), SMALL_CARD_PADDING_LEFT, SMALL_CARD_PADDING_TOP, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, false);
					else
					{
						this.drawImage(art, SMALL_CARD_ART_LEFT, SMALL_CARD_ART_TOP, SMALL_CARD_ART_WIDTH, SMALL_CARD_ART_HEIGHT, null);
						this.drawCardText(characteristics.name, getFont(), SMALL_CARD_PADDING_LEFT, SMALL_CARD_PADDING_TOP, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, false);
						this.drawCardText(characteristics.name, getFont(), SMALL_CARD_PADDING_LEFT - 1, SMALL_CARD_PADDING_TOP - 1, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT), false, false, java.awt.Color.WHITE);
					}
				}

				boolean isCreature = characteristics.types.contains(Type.CREATURE);
				if(isCreature)
				{
					this.drawPowerToughnessBox(characteristics, SMALL_CARD_PT_BOX_LEFT, SMALL_CARD_PT_BOX_TOP, true);
					this.drawCardText(characteristics.power + "/" + characteristics.toughness, getFont(), SMALL_CARD_PT_TEXT_LEFT, SMALL_CARD_PT_TEXT_TOP, SMALL_CARD_PT_DIMENSIONS, true, false);
				}
				if(characteristics.types.contains(Type.PLANESWALKER))
				{
					int offset = isCreature ? 39 : 0;
					this.drawImage(getImage("smallframes/loyaltybox.png"), SMALL_CARD_PT_BOX_LEFT - offset, SMALL_CARD_PT_BOX_TOP, null);
					this.drawCardText(Integer.toString(loyaltyOf(object, SanitizedGameObject.CharacteristicSet.ACTUAL)), getFont(), SMALL_CARD_PT_TEXT_LEFT - offset, SMALL_CARD_PT_TEXT_TOP, SMALL_CARD_PT_DIMENSIONS, true, false, java.awt.Color.WHITE);
				}

				if(renderDamage && object.damage > 0)
				{
					java.awt.FontMetrics f = this.getFontMetrics();
					int x = SMALL_CARD.width - SMALL_CARD_DAMAGE_RIGHT;
					int y = SMALL_CARD.height - SMALL_CARD_DAMAGE_BOTTOM - f.getHeight();
					this.drawCardTextRightAligned(Integer.toString(((SanitizedGameObject)o).damage), x, y, java.awt.Color.RED);
				}

				if(renderCounters)
				{
					int countersPerRow = 10;
					int maxRows = 3;
					java.util.List<Counter> counters = ((SanitizedGameObject)o).counters;
					if(counters.size() > maxRows * countersPerRow)
					{
						int x = SMALL_CARD_PADDING_LEFT;
						int height = this.getFontMetrics().getHeight();
						int y = SMALL_CARD_ART_TOP + SMALL_CARD_ART_HEIGHT - height - 1;
						this.drawCardText(counters.size() + " counters", this.getFont(), x, y, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, height), false, false);
						this.drawCardText(counters.size() + " counters", this.getFont(), x - 1, y - 1, new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, height), false, false, java.awt.Color.WHITE);
					}
					else
					{
						java.util.Collections.sort(counters);

						java.awt.Color oldColor = this.getColor();
						int counterSize = 5;
						int counterSpace = 7;
						int i = 0;
						for(Counter c: counters)
						{
							// TODO : customize these colors
							java.awt.Color color = java.awt.Color.WHITE;
							if(c.getType() == Counter.CounterType.PLUS_ONE_PLUS_ONE)
								color = java.awt.Color.GREEN;
							else if(c.getType() == Counter.CounterType.MINUS_ONE_MINUS_ONE)
								color = java.awt.Color.RED;

							int col = i % countersPerRow;
							int x = SMALL_CARD_ART_LEFT + SMALL_CARD_ART_WIDTH - (col + 1) * counterSpace;

							int row = i / countersPerRow;
							int y = SMALL_CARD_ART_TOP + SMALL_CARD_ART_HEIGHT - (row + 1) * counterSpace;

							this.setColor(color);
							fillRect(x, y, counterSize, counterSize);
							this.setColor(java.awt.Color.BLACK);
							drawRect(x, y, counterSize, counterSize);

							i++;
						}
						this.setColor(oldColor);
					}
				}

				int options = 0;
				for(SanitizedGameObject.CharacteristicSet option: getLargeCardDisplayOptions(object))
				{
					++options;

					java.awt.Rectangle rect = getSmallCardOptionRect(false, false, new java.awt.Point(0, 0), options);
					this.drawImage(getImage("icons/" + option.name().toLowerCase() + ".png"), (int)(rect.getX()), (int)(rect.getY()), null);
				}
			}
		}
	}

	private static java.awt.Image getCardArt(String cardName, boolean getLargeArt, final SanitizedGameObject object)
	{
		if(cardArts == null)
			return null;

		final String fileName = cardName + ".jpg";
		if(imageCache.containsKey(fileName))
		{
			// Small images are prepended with an *
			java.awt.Image value = imageCache.get((getLargeArt ? "" : "*") + fileName);
			if((value == IMAGE_CACHE_NO_IMAGE) || (value == IMAGE_CACHE_WORKING))
				return null;
			return value;
		}

		// Don't spawn hundreds of concurrent threads to retrieve the same
		// resource
		imageCache.put(fileName, IMAGE_CACHE_WORKING);
		imageCache.put("*" + fileName, IMAGE_CACHE_WORKING);

		new Thread("GetCardArt " + fileName)
		{
			@Override
			public void run()
			{
				try
				{
					java.nio.file.Path cardPath = cardArts.resolve(fileName);

					if(readCardArtIntoImageCache(fileName, cardPath))
						return;

					// Try to download the art only for cards that aren't a
					// face-down creature
					if(object.isCard && !cardName.isEmpty())
					{
						// TODO: make this URI configurable somehow
						try(java.io.InputStream in = new java.net.URI("http", "mtgimage.com", "/card/" + cardName + "-crop.jpg", null).toURL().openStream())
						{
							java.nio.file.Files.copy(in, cardPath);
							if(readCardArtIntoImageCache(fileName, cardPath))
								return;
						}
						catch(java.net.URISyntaxException e)
						{
							LOG.log(java.util.logging.Level.SEVERE, "Something is wrong with the card name " + cardName + " such that a URI couldn't be created with it", e);
						}
						catch(java.io.IOException e)
						{
							LOG.log(java.util.logging.Level.INFO, "Could not download art for " + cardName, e);
						}
					}
				}
				catch(java.nio.file.InvalidPathException e)
				{
					// Most abilities won't have names that can have valid
					// paths, so silently ignore the problem
				}

				// If we don't find the file, assume we won't find it any other
				// time and save some reads.
				imageCache.put(fileName, IMAGE_CACHE_NO_IMAGE);
				imageCache.put("*" + fileName, IMAGE_CACHE_NO_IMAGE);
			}
		}.start();

		return null;
	}

	private int loyaltyOf(SanitizedGameObject o, SanitizedGameObject.CharacteristicSet set)
	{
		if(o.zoneID != this.state.battlefield)
			return o.characteristics[0].get(set).printedLoyalty;

		int loyaltyCounters = 0;
		for(Counter c: o.counters)
			if(c.getType() == Counter.CounterType.LOYALTY)
				loyaltyCounters++;
		return loyaltyCounters;
	}

	public void popTransform()
	{
		this.setTransform(this.transformStack.pop());
	}

	public void pushTransform()
	{
		this.transformStack.push(this.getTransform());
	}

	/**
	 * Draws a large number, indicating that the user is to be dividing
	 * something. This function assumes that the graphics have been translated
	 * to the upper left corner of the rectangle representing the card or
	 * card-like object over which the number is to be drawn.
	 *
	 * @param division The number to draw.
	 */
	public void drawDivision(int division)
	{
		java.awt.Dimension cardBounds = new java.awt.Dimension(SMALL_CARD_TEXT_WIDTH, SMALL_CARD_TOTAL_TEXT_HEIGHT);
		this.drawCardText(Integer.toString(division), getFont().deriveFont(104f), SMALL_CARD_PADDING_LEFT, SMALL_CARD_PADDING_TOP, cardBounds, true, false);
	}
}

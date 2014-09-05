package org.rnd.jmagic.gui;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.sanitized.*;

public class JMagicTextPane extends javax.swing.JTextPane
{
	private static final long serialVersionUID = 1L;

	private final boolean largeIcons;

	public final static java.util.Map<String, String> ALL_KEYWORDS = new java.util.HashMap<>();
	static
	{
		ALL_KEYWORDS.put("deathtouch", "Any amount of damage this creature deals to a creature is enough to destroy it.");
		ALL_KEYWORDS.put("defender", "This creature can't attack.");
		ALL_KEYWORDS.put("double strike", "This creature deals both first strike and regular combat damage.");
		ALL_KEYWORDS.put("first strike", "This creature strike deals combat damage before creatures without first strike or double strike.");
		ALL_KEYWORDS.put("flash", "You may cast a spell with flash any time you could cast an instant.");
		ALL_KEYWORDS.put("flying", "This creature can't be blocked except by creatures with flying or reach.");
		ALL_KEYWORDS.put("haste", "This creature can attack and (T) as soon as it comes under your control.");
		ALL_KEYWORDS.put("hexproof", "A permanent or player with hexproof can't be the target of spells or abilities opponents control.");
		ALL_KEYWORDS.put("indestructible", "Effects that say \"destroy\" don't destroy a permanent with indestructible. A creature with indestructible can't be destroyed by damage.");
		ALL_KEYWORDS.put("intimidate", "This creature can't be blocked except by artifact creatures and creatures that share a color with it.");
		ALL_KEYWORDS.put("plainswalk", "This creature can't be blocked as long as defending player controls a Plains.");
		ALL_KEYWORDS.put("islandwalk", "This creature can't be blocked as long as defending player controls an Island.");
		ALL_KEYWORDS.put("swampwalk", "This creature can't be blocked as long as defending player controls a Swamp.");
		ALL_KEYWORDS.put("mountainwalk", "This creature can't be blocked as long as defending player controls a Mountain.");
		ALL_KEYWORDS.put("forestwalk", "This creature can't be blocked as long as defending player controls a Forest.");
		ALL_KEYWORDS.put("lifelink", "Damage dealt by a source with lifelink also causes its controller to gain that much life.");
		ALL_KEYWORDS.put("protection", "A permanent or player with protection can't be blocked, targeted, dealt damage, or enchanted by anything with the stated quality.");
		ALL_KEYWORDS.put("reach", "This creature can block creatures with flying.");
		ALL_KEYWORDS.put("shroud", "A permanent or player with shroud can't be the target of spells or abilities.");
		ALL_KEYWORDS.put("trample", "If this creature would assign enough damage to its blockers to destroy them, its controller may have it assign the rest of its damage to defending player or planeswalker.");
		ALL_KEYWORDS.put("vigilance", "Attacking doesn't cause this creature to tap.");
		ALL_KEYWORDS.put("banding", "Any creatures with banding, and up to one without, can attack in a band. Bands are blocked as a group. If any creatures with banding you control are blocking or being blocked by a creature, you divide that creature's combat damage, not its controller, among any of the creatures it's being blocked by or blocking.");
		ALL_KEYWORDS.put("bands with other", "Any creatures with the stated quality can attack in a band as long as at least one has the appropriate \"bands with other\" ability. Bands are blocked as a group. If at least two creatures with the stated quality you control, one of which has the appropriate \"bands with other \" ability, are blocking or being blocked by the same creature, you divide that creature's combat damage, not its controller, among any of the creatures it's being blocked by or blocking.");
		ALL_KEYWORDS.put("rampage", "Whenever a creature with rampage N becomes blocked, it gets +N/+N until end of turn for each creature blocking it beyond the first.");
		ALL_KEYWORDS.put("cumulative upkeep", "At the beginning of your upkeep, put an age counter on this permanent, then sacrifice it unless you pay its upkeep cost.");
		ALL_KEYWORDS.put("flanking", "Whenever a creature without flanking blocks this creature, the blocking creature gets -1/-1 until end of turn.");
		ALL_KEYWORDS.put("phasing", "This phases in or out before you untap during each of your untap steps. While it's phased out, it's treated as though it doesn't exist.");
		ALL_KEYWORDS.put("buyback", "You may pay the buyback cost as you cast this spell. If you do, put this card into your hand as it resolves.");
		ALL_KEYWORDS.put("shadow", "This creature can block or be blocked only by creatures with shadow.");
		ALL_KEYWORDS.put("cycling", "Pay this card's cycling cost and discard it: Draw a card.");
		ALL_KEYWORDS.put("basic landcycling", "Pay this card's cycling cost and discard it: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("plainscycling", "Pay this card's cycling cost and discard it: Search your library for a plains card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("islandcycling", "Pay this card's cycling cost and discard it: Search your library for a island card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("swampcycling", "Pay this card's cycling cost and discard it: Search your library for a swamp card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("mountaincycling", "Pay this card's cycling cost and discard it: Search your library for a mountain card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("forestcycling", "Pay this card's cycling cost and discard it: Search your library for a forest card, reveal it, and put it into your hand. Then shuffle your library.");
		ALL_KEYWORDS.put("echo", "At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.");
		ALL_KEYWORDS.put("horsemanship", "This creature can't be blocked except by creatures with horsemanship.");
		ALL_KEYWORDS.put("fading", "This permanent enters the battlefield with N fade counters on it. At the beginning of your upkeep, remove a fade counter from it. If you can't, sacrifice it.");
		ALL_KEYWORDS.put("kicker", "You may pay this card's kicker cost as you cast it; if you do, it's \"kicked\".");
		ALL_KEYWORDS.put("flashback", "You may cast this card from your graveyard for its flashback cost. Then exile it.");
		ALL_KEYWORDS.put("madness", "If you discard this card, you may cast it for its madness cost instead of putting it into your graveyard.");
		ALL_KEYWORDS.put("fear", "This creature can't be blocked except by artifact creatures and/or black creatures.");
		ALL_KEYWORDS.put("morph", "You may cast this card face down as a 2/2 creature for (3). Turn it face up any time for its morph cost.");
		ALL_KEYWORDS.put("amplify", "As this creature enters the battlefield, put a +1/+1 counter on it for each card sharing a creature type with it you reveal from your hand.");
		ALL_KEYWORDS.put("provoke", "When this attacks, you may have target creature defending player controls untap and block it if able.");
		ALL_KEYWORDS.put("storm", "When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.");
		ALL_KEYWORDS.put("affinity", "This card costs (1) less to cast for each permanent with the stated quality you control.");
		ALL_KEYWORDS.put("entwine", "Choose both if you pay the entwine cost.");
		ALL_KEYWORDS.put("modular", "This enters the battlefield with N +1/+1 counters on it. When it dies, you may put its +1/+1 counters on target artifact creature.");
		ALL_KEYWORDS.put("sunburst", "This enters the battlefield with a +1/+1 counter or charge counter on it for each color of mana spent to cast it.");
		ALL_KEYWORDS.put("bushido", "When this blocks or becomes blocked, it get +N/+N until end of turn.");
		ALL_KEYWORDS.put("soulshift", "When this creature dies, you may return target Spirit card with converted mana cost N or less from your graveyard to your hand.");
		ALL_KEYWORDS.put("splice", "As you cast a spell with the stated quality, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.");
		ALL_KEYWORDS.put("offering", "You may cast this card any time you could cast an instant by sacrificing a permanent with the stated type and paying the difference in mana costs between this and the sacrificed permanent. Mana cost includes color.");
		ALL_KEYWORDS.put("ninjutsu", "Pay this card's ninjutsu cost, return an unblocked attacker you control to hand: Put this card onto the battlefield tapped and attacking.");
		ALL_KEYWORDS.put("epic", "For the rest of the game, you can't cast spells. At the beginning of each of your upkeeps, copy this spell except for its epic ability. You may choose new targets for the copy.");
		ALL_KEYWORDS.put("convoke", "Your creatures can help cast this spell. Each creature you tap while casting this spell pays for (1) or one mana of that creature's color.");
		ALL_KEYWORDS.put("dredge", "If you would draw a card, instead you may put exactly N cards from the top of your library into your graveyard. If you do, return this card from your graveyard to your hand. Otherwise, draw a card.");
		ALL_KEYWORDS.put("transmute", "Pay this card's transmute cost and discard it: Search your library for a card with the same converted mana cost as this card, reveal it, and put it into your hand. Then shuffle your library. Transmute only as a sorcery.");
		ALL_KEYWORDS.put("bloodthirst", "If an opponent was dealt damage this turn, this creature enters the battlefield with N +1/+1 counters on it.");
		ALL_KEYWORDS.put("haunt", "When this creature dies or when this spell is put into a graveyard after resolving, exile it haunting target creature.");
		ALL_KEYWORDS.put("replicate", "When you cast this spell, copy it for each time you paid its replicate cost. You may choose new targets for the copies.");
		ALL_KEYWORDS.put("forecast", "Activate this ability only during your upkeep and only once each turn.");
		ALL_KEYWORDS.put("graft", "This creature enters the battlefield with N +1/+1 counters on it. Whenever another creature enters the battlefield, you may move a +1/+1 counter from this creature onto it.");
		ALL_KEYWORDS.put("recover", "When this creature is put into your graveyard from the battlefield, you may pay its recover cost. If you do, return this card from your graveyard to your hand. Otherwise, exile it.");
		ALL_KEYWORDS.put("ripple", "When you cast this spell, you may reveal the top N cards of your library. You may cast any revealed cards with the same name as this spell without paying their mana costs. Put the rest on the bottom of your library.");
		ALL_KEYWORDS.put("split second", "As long as this spell is on the stack, player's cant cast spells or activate abilities that aren't mana abilities.");
		ALL_KEYWORDS.put("suspend", "Rather than cast this card from your hand, you may pay its suspend cost and exile it with N time counters on it. At the beginning of your upkeep, remove a time counter. When the last is removed, cast it without paying its mana cost.");
		ALL_KEYWORDS.put("vanishing", "This permanent enters the battlefield with N time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.");
		ALL_KEYWORDS.put("absorb", "If a source would deal damage to a creature with absorb, prevent N of that damage.");
		ALL_KEYWORDS.put("aura swap", "Pay this Aura's aura swap cost: exchange this Aura with an Aura card in your hand.");
		ALL_KEYWORDS.put("delve", "Each card you exile from your graveyard while casting this spell pays for (1).");
		ALL_KEYWORDS.put("equip", "Pay this Equipment's equip cost: Attach to target creature you control. Equip only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the creature leaves.");
		ALL_KEYWORDS.put("fortify", "Pay this Fortification's fortify cost: Attach to target land you control. Fortify only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the land leaves.");
		ALL_KEYWORDS.put("frenzy", "Whenever this creature attacks and isn't blocked, it gets +N/+0 until end of turn.");
		ALL_KEYWORDS.put("gravestorm", "When you cast this spell, copy it for each permanent put into a graveyard this turn. You may choose new targets for the copies.");
		ALL_KEYWORDS.put("poisonous", "Whenever this creature deals combat damage to a player, that player gets N poison counters. A player with ten or more poison counters loses the game.");
		ALL_KEYWORDS.put("transfigure", "Pay this permanent's transfigure cost and sacrifice it: Search your library for a creature card with the same converted mana cost as this card and put that card onto the battlefield. Then shuffle your library. Transfigure only as a sorcery.");
		ALL_KEYWORDS.put("champion", "When this enters the battlefield, sacrifice it unless you exile another permanent with the stated quality you control. When this leaves the battlefield, that card returns to the battlefield.");
		ALL_KEYWORDS.put("changeling", "This card is every creature type at all times.");
		ALL_KEYWORDS.put("evoke", "You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.");
		ALL_KEYWORDS.put("hideaway", "This permanent enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.");
		ALL_KEYWORDS.put("prowl", "You may cast this for its prowl cost if you dealt combat damage to a player this turn with a source that had any of this spell's creature types.");
		ALL_KEYWORDS.put("reinforce", "Pay this card's reinforce cost and discard it: Put N +1/+1 counters on target creature.");
		ALL_KEYWORDS.put("conspire", "As you cast this spell, you may tap two untapped creatures you control that share a color with it. When you do, copy it and you may choose new targets for the copy.");
		ALL_KEYWORDS.put("persist", "When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.");
		ALL_KEYWORDS.put("wither", "This deals damage to creatures in the form of -1/-1 counters.");
		ALL_KEYWORDS.put("retrace", "You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.");
		ALL_KEYWORDS.put("devour", "As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with N +1/+1 counters on it for each sacrificed creature.");
		ALL_KEYWORDS.put("exalted", "Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.");
		ALL_KEYWORDS.put("unearth", "Pay this card's unearth cost: Return it from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.");
		ALL_KEYWORDS.put("cascade", "When you cast this spell, exile cards from the top of your library until you exile a nonland card that costs less. You may cast it without paying its mana cost. Put the exiled cards on the bottom in a random order.");
		ALL_KEYWORDS.put("annihilator", "Whenever this creature attacks, defending player sacrifices N permanents.");
		ALL_KEYWORDS.put("level up", "Pay this creature's level up cost: Put a level up counter on it. Level up only as a sorcery.");
		ALL_KEYWORDS.put("rebound", "Exile this spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast this card from exile without paying its mana cost.");
		ALL_KEYWORDS.put("totem armor", "If enchanted creature would be destroyed, instead remove all damage from it and destroy this Aura.");
		ALL_KEYWORDS.put("infect", "This creature deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.");
		ALL_KEYWORDS.put("battle cry", "Whenever this creature attacks, each other attacking creature gets +1/+0 until end of turn.");
		ALL_KEYWORDS.put("living weapon", "When this Equipment enters the battlefield, put a 0/0 black Germ creature token onto the battlefield, then attach this to it.");
		ALL_KEYWORDS.put("undying", "When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.");
		ALL_KEYWORDS.put("miracle", "You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.");
		ALL_KEYWORDS.put("soulbond", "You may pair this creature with another unpaired creature when either enters the battlefield. They remain paired for as long as you control both of them.");
		ALL_KEYWORDS.put("overload", "You may cast this spell for its overload cost. If you do, change its text by replacing all instances of \"target\" with \"each.\"");
		ALL_KEYWORDS.put("scavenge", "Pay this card's scavenge cost and exile it from your graveyard: Put a number of +1/+1 counters equal to this card's power on target creature. Scavenge only as a sorcery.");
		ALL_KEYWORDS.put("unleash", "You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.");
		ALL_KEYWORDS.put("cipher", "Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.");
		ALL_KEYWORDS.put("evolve", "Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.");
		ALL_KEYWORDS.put("extort", "Whenever you cast a spell, you may pay (W/B). If you do, each opponent loses 1 life and you gain that much life.");
		ALL_KEYWORDS.put("fuse", "You may cast one or both halves of this card from your hand.");
		ALL_KEYWORDS.put("bestow", "If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.");
		ALL_KEYWORDS.put("tribute", "As this creature enters the battlefield, an opponent of your choice may place N +1/+1 counters on it.");
	}

	public JMagicTextPane()
	{
		this(false);
	}

	public JMagicTextPane(boolean largeIcons)
	{
		this.largeIcons = largeIcons;
		this.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
		this.setEditable(false);

		javax.swing.text.DefaultCaret caret = (javax.swing.text.DefaultCaret)(this.getCaret());
		caret.setUpdatePolicy(javax.swing.text.DefaultCaret.NEVER_UPDATE);

		this.setOpaque(false);
	}

	public static String getModeChoiceText(org.rnd.jmagic.engine.Set number)
	{
		Integer lower = Minimum.get(number);
		Integer upper = Maximum.get(number);
		if(null == lower)
			lower = 1;

		if(lower.equals(upper))
			return "Choose " + org.rnd.util.NumberNames.get(lower);
		else if(upper == null)
			return "Choose " + org.rnd.util.NumberNames.get(lower) + " or more";
		else if(lower == 1 && upper == 2)
			return "Choose one or both";
		else
			return "Choose between " + org.rnd.util.NumberNames.get(lower) + " and " + org.rnd.util.NumberNames.get(upper);
	}

	@Override
	public void setText(java.lang.String text)
	{
		this.setText(text, null);
	}

	/**
	 * Sets the text of this text pane to the specified text, and optionally
	 * returns text intended to help the player figure out what keywords do.
	 *
	 * @param text The text to set.
	 * @param bolds A map of start->end indicating what portion of the text
	 * should be bold.
	 * @param help Whether to return help text for the keywords in the text.
	 * @return
	 */
	public void setText(java.lang.String text, java.util.Map<Integer, Integer> bolds)
	{
		super.setText(text);

		javax.swing.text.StyledDocument document = this.getStyledDocument();
		java.util.Map<String, javax.swing.text.Style> styles = new java.util.HashMap<String, javax.swing.text.Style>();

		// Use the style from the first position in the text before any styles
		// have been applied
		javax.swing.text.Style defaultStyle = document.getLogicalStyle(0);
		javax.swing.text.Style halfSize = document.addStyle("half-size", defaultStyle);
		javax.swing.text.StyleConstants.setFontSize(halfSize, javax.swing.text.StyleConstants.getFontSize(defaultStyle) / 6);

		int leftParenthesis = text.indexOf('(');
		while(-1 != leftParenthesis)
		{
			int rightParenthesis = text.indexOf(')', leftParenthesis);
			String symbol = text.substring(leftParenthesis, rightParenthesis + 1);

			javax.swing.text.Style style;
			if(styles.containsKey(symbol))
				style = styles.get(symbol);
			else
			{
				style = document.addStyle(symbol, null);
				javax.swing.text.StyleConstants.setIcon(style, new javax.swing.ImageIcon(CardGraphics.getIcon(symbol, !this.largeIcons)));
				styles.put(symbol, style);
			}

			document.setCharacterAttributes(leftParenthesis, rightParenthesis - leftParenthesis + 1, style, true);
			leftParenthesis = text.indexOf('(', leftParenthesis + 1);
		}

		if(null != bolds)
		{
			for(java.util.Map.Entry<Integer, Integer> e: bolds.entrySet())
			{
				javax.swing.text.Style boldStyle = document.addStyle("bold", null);
				javax.swing.text.StyleConstants.setBold(boldStyle, true);
				document.setCharacterAttributes(e.getKey(), e.getValue() - e.getKey() + 1, boldStyle, false);
			}
		}

		int doubleLineBreak = text.indexOf("\n\n");
		while(-1 != doubleLineBreak)
		{
			document.setCharacterAttributes(doubleLineBreak, 2, halfSize, true);
			doubleLineBreak = text.indexOf("\n\n", doubleLineBreak + 1);
		}
	}

	/**
	 * Sets the text of this text pane to the text of the specified object, and
	 * returns help text describing the keywords on that object.
	 *
	 * @param o The object whose text box to use.
	 * @param state The state in which o exists.
	 * @param displayOption Which set of characteristics to use.
	 */
	public void setText(SanitizedGameObject o, SanitizedGameState state, SanitizedGameObject.CharacteristicSet displayOption)
	{
		StringBuilder textBuilder = new StringBuilder();
		boolean firstLine = true;

		if(o.counters.size() > 0)
		{
			java.util.Map<Counter.CounterType, Integer> counterQuantities = new java.util.HashMap<Counter.CounterType, Integer>();
			for(Counter counter: o.counters)
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
				textBuilder.append((firstLine ? "" : "\n\n") + count + " " + type + (count == 1 ? "" : "s"));
				firstLine = false;
			}
		}

		if(o.zoneID == state.stack && o.valueOfX != -1)
		{
			textBuilder.append((firstLine ? "" : "\n\n") + "X is " + o.valueOfX + ".");
			firstLine = false;
		}

		if(!o.otherLinks.isEmpty())
		{
			textBuilder.append((firstLine ? "" : "\n\n") + o.otherLinks);
			firstLine = false;
		}

		SanitizedCharacteristics c = o.characteristics.get(displayOption);

		if(o instanceof SanitizedActivatedAbility)
		{
			SanitizedActivatedAbility a = (SanitizedActivatedAbility)o;
			if(!firstLine)
				textBuilder.append("\n\n");

			boolean firstCost = true;
			// Activated abilities always have mana costs, so don't bother with
			// the null check
			if(0 != c.manaCost.size())
			{
				textBuilder.append(c.manaCost);
				firstCost = false;
			}

			if(a.costsTap)
			{
				if(!firstCost)
					textBuilder.append(", ");
				textBuilder.append("(T)");
				firstCost = false;
			}

			if(a.costsUntap)
			{
				if(!firstCost)
					textBuilder.append(", ");
				textBuilder.append("(U)");
				firstCost = false;
			}

			for(String cost: c.costs)
			{
				if(!firstCost)
					textBuilder.append(", ");
				textBuilder.append(cost);
				firstCost = false;
			}

			if(firstCost && (0 == c.manaCost.size()))
			{
				textBuilder.append(c.manaCost);
				firstCost = false;
			}

			textBuilder.append(": ");
			// Leave firstLine true since we don't want the effects that make up
			// the ability to break any lines
		}
		else if(0 != c.costs.size())
		{
			if(!firstLine)
				textBuilder.append("\n\n");
			textBuilder.append("As an additional cost to cast " + o.name + ",");

			for(String cost: c.costs)
				textBuilder.append(" " + cost);

			textBuilder.append(".");
			firstLine = false;
		}

		java.util.Map<Integer, Integer> bolds = new java.util.HashMap<Integer, Integer>();
		java.util.List<String> keywordStrings = new java.util.LinkedList<String>();
		abilityLoop: for(int abilityID: c.abilities)
		{
			// -1 is a marker for where on the card the modes with effects are
			if(abilityID == -1)
			{
				if(!firstLine)
					textBuilder.append("\n\n");
				firstLine = false;

				String betweenModes = "\n\n";
				if(c.modes.size() != 1 && c.modes.size() != c.selectedModes.size())
				{
					textBuilder.append(getModeChoiceText(c.numModes) + " \u2014\n\u2022 ");
					betweenModes = "\n\u2022 ";
				}

				int modesCounted = 0;
				for(SanitizedMode mode: c.modes)
				{
					if(modesCounted != 0)
						textBuilder.append(betweenModes);

					StringBuffer effects = new StringBuffer();
					for(String effect: mode.effects)
					{
						if(!(effect.isEmpty()))
						{
							if(effects.length() != 0)
								effects.append(' ');
							effects.append(effect);
						}
					}

					int boldStart = -1;
					if(c.modes.size() > 1 && c.selectedModes.contains(modesCounted + 1))
						boldStart = textBuilder.length();
					textBuilder.append(effects);
					if(-1 != boldStart)
						bolds.put(boldStart, textBuilder.length());

					modesCounted++;
				}
				continue;
			}

			SanitizedIdentified ability = state.get(abilityID);
			if(ability.isKeyword)
			{
				if(keywordStrings.isEmpty())
					keywordStrings.add(ability.name);
				else
					keywordStrings.add(ability.name.substring(0, 1).toLowerCase() + ability.name.substring(1));
				continue abilityLoop;
			}

			if(!keywordStrings.isEmpty())
			{
				StringBuilder keywordText = org.rnd.util.SeparatedList.get("", keywordStrings);
				textBuilder.append((firstLine ? "" : "\n\n") + keywordText);
				firstLine = false;
				keywordStrings.clear();
			}
			textBuilder.append((firstLine ? "" : "\n\n") + ability.name);
			firstLine = false;
		}

		if(!keywordStrings.isEmpty())
		{
			StringBuilder keywordText = org.rnd.util.SeparatedList.get("", keywordStrings);
			textBuilder.append((firstLine ? "" : "\n\n") + keywordText);
			firstLine = false;
			keywordStrings.clear();
		}

		this.setText(textBuilder.toString().trim(), bolds);
	}

	/**
	 * @return Help text describing the keyword names in this text pane.
	 */
	public String getHelpText()
	{
		String text = super.getText();

		// find all instances of keywords so we can display their reminder text.
		// we don't just do this while building the text because it's possible
		// that some of these instances are mentions of the keyword in other
		// abilities rather than the card actually having the keyword (like
		// 'enchanted creature has flying')
		java.util.List<String> keywordsFound = new java.util.LinkedList<>();
		for(String keyword: ALL_KEYWORDS.keySet())
		{
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b" + keyword + "\\b", java.util.regex.Pattern.CASE_INSENSITIVE);
			if(pattern.matcher(text).find())
				keywordsFound.add(keyword);
		}

		if(keywordsFound.isEmpty())
			return null;

		java.util.List<String> helpTextList = new java.util.LinkedList<>();
		for(String keyword: keywordsFound)
			helpTextList.add(keyword + ": " + ALL_KEYWORDS.get(keyword));

		return org.rnd.util.SeparatedList.get("\n\n", "", helpTextList).toString();
	}
}

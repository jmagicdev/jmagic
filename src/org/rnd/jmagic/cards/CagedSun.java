package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Caged Sun")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({})
public final class CagedSun extends Card
{
	public static final class ColorChoice extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ColorChoice(GameState state)
		{
			super(state, "Caged Sun");
			this.getLinkManager().addLinkClass(CagedSunAbility1.class);
			this.getLinkManager().addLinkClass(CagedSunAbility2.class);
		}
	}

	public static final class CagedSunAbility1 extends StaticAbility
	{
		public CagedSunAbility1(GameState state)
		{
			super(state, "Creatures you control of the chosen color get +1/+1.");

			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator creatures = Intersect.instance(CREATURES_YOU_CONTROL, HasColor.instance(chosenColor));

			this.addEffectPart(modifyPowerAndToughness(creatures, +1, +1));

			this.getLinkManager().addLinkClass(ColorChoice.class);
		}
	}

	public static final class ManaFromLandOfColor implements EventPattern
	{
		private SimpleEventPattern mana;
		private SetGenerator color;

		public ManaFromLandOfColor(SetGenerator color)
		{
			this.mana = new SimpleEventPattern(EventType.ADD_MANA);
			this.mana.put(EventType.Parameter.PLAYER, You.instance());
			this.mana.put(EventType.Parameter.SOURCE, LandPermanents.instance());
			this.color = color;
		}

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			if(!this.mana.match(event, object, state))
				return false;
			Set colors = this.color.evaluate(state, object);
			for(ManaSymbol m: event.getResult().getAll(ManaSymbol.class))
				for(Color c: m.colors)
					if(colors.contains(c))
						return true;
			return false;
		}

		@Override
		public boolean looksBackInTime()
		{
			return false;
		}

		@Override
		public boolean matchesManaAbilities()
		{
			return true;
		}
	}

	public static final class CagedSunAbility2 extends EventTriggeredAbility
	{
		public CagedSunAbility2(GameState state)
		{
			super(state, "Whenever a land's ability adds one or more mana of the chosen color to your mana pool, add one additional mana of that color to your mana pool.");

			SetGenerator chosenColor = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			EventPattern triggerEvent = new ManaFromLandOfColor(chosenColor);
			this.addPattern(triggerEvent);

			this.addEffect(addManaToYourManaPoolFromAbility(chosenColor, "Add one additional mana of that color to your mana pool."));

			this.getLinkManager().addLinkClass(ColorChoice.class);
		}
	}

	public CagedSun(GameState state)
	{
		super(state);

		// As Caged Sun enters the battlefield, choose a color.
		this.addAbility(new ColorChoice(state));

		// Creatures you control of the chosen color get +1/+1.
		this.addAbility(new CagedSunAbility1(state));

		// Whenever a land's ability adds one or more mana of the chosen color
		// to your mana pool, add one additional mana of that color to your mana
		// pool.
		this.addAbility(new CagedSunAbility2(state));
	}
}

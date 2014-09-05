package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Serpent of the Endless Sea")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SerpentoftheEndlessSea extends Card
{
	public static final class SerpentCDA extends CharacteristicDefiningAbility
	{
		public SerpentCDA(GameState state)
		{
			super(state, "Serpent of the Endless Sea's power and toughness are each equal to the number of Islands you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator value = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND)));

			this.addEffectPart(setPowerAndToughness(This.instance(), value, value));
		}
	}

	public static final class SerpentRestriction extends StaticAbility
	{
		public SerpentRestriction(GameState state)
		{
			super(state, "Serpent of the Endless Sea can't attack unless defending player controls an Island.");

			SetGenerator restriction = Not.instance(Intersect.instance(ControlledBy.instance(DefendingPlayer.instance(This.instance())), HasSubType.instance(SubType.ISLAND)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
		}
	}

	public SerpentoftheEndlessSea(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Serpent of the Endless Sea's power and toughness are each equal to
		// the number of Islands you control.
		this.addAbility(new SerpentCDA(state));

		// Serpent of the Endless Sea can't attack unless defending player
		// controls an Island.
		this.addAbility(new SerpentRestriction(state));
	}
}

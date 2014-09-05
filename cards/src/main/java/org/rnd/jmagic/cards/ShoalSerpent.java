package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shoal Serpent")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ShoalSerpent extends Card
{
	public static final class LandfallAttacker extends EventTriggeredAbility
	{
		public LandfallAttacker(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, Shoal Serpent loses defender until end of turn.");

			this.addPattern(landfall());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Defender.class));

			this.addEffect(createFloatingEffect("Shoal Serpent loses defender until end of turn", part));
		}
	}

	public ShoalSerpent(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new LandfallAttacker(state));
	}
}

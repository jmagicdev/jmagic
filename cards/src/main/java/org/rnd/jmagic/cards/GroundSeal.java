package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ground Seal")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Odyssey.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GroundSeal extends Card
{
	public static final class GroundSealAbility0 extends EventTriggeredAbility
	{
		public GroundSealAbility0(GameState state)
		{
			super(state, "When Ground Seal enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public static final class GroundSealAbility1 extends StaticAbility
	{
		public GroundSealAbility1(GameState state)
		{
			super(state, "Cards in graveyards can't be the targets of spells or abilities.");

			SetGenerator inYards = InZone.instance(GraveyardOf.instance(Players.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, inYards);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(SetPattern.EVERYTHING));
			this.addEffectPart(part);
		}
	}

	public GroundSeal(GameState state)
	{
		super(state);

		// When Ground Seal enters the battlefield, draw a card.
		this.addAbility(new GroundSealAbility0(state));

		// Cards in graveyards can't be the targets of spells or abilities.
		this.addAbility(new GroundSealAbility1(state));
	}
}

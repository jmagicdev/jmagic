package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Windborn Muse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class WindbornMuse extends Card
{
	// Creatures can't attack you unless their controller pays 2 for each
	// creature he or she controls that's attacking you.
	public static final class WindbornMuseAbility extends StaticAbility
	{
		public WindbornMuseAbility(GameState state)
		{
			super(state, "Creatures can't attack you unless their controller pays (2) for each creature he or she controls that's attacking you.");
			SetGenerator you = ControllerOf.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, you);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			this.addEffectPart(part);
		}
	}

	public WindbornMuse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new WindbornMuseAbility(state));
	}
}

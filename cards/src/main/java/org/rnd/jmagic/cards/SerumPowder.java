package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serum Powder")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class SerumPowder extends Card
{
	public static final class SerumPowderAbility1 extends StaticAbility
	{
		public SerumPowderAbility1(GameState state)
		{
			super(state, "Any time you could mulligan and Serum Powder is in your hand, you may exile all the cards from your hand, then draw that many cards.");

			this.canApply = Intersect.instance(This.instance(), InZone.instance(HandOf.instance(Players.instance())));

			EventFactory exile = exile(InZone.instance(HandOf.instance(You.instance())), "Exile all the cards from your hand,");
			EventFactory draw = drawCards(You.instance(), Count.instance(EffectResult.instance(exile)), "then draw that many cards");
			EventFactory serumPowderMulligan = sequence(exile, draw);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MULLIGAN_OPTION);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(serumPowderMulligan));
			this.addEffectPart(part);
		}
	}

	public SerumPowder(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// Any time you could mulligan and Serum Powder is in your hand, you may
		// exile all the cards from your hand, then draw that many cards. (You
		// can do this in addition to taking mulligans.)
		this.addAbility(new SerumPowderAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Prophetic Flamespeaker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class PropheticFlamespeaker extends Card
{
	public static final class PropheticFlamespeakerAbility1 extends EventTriggeredAbility
	{
		public PropheticFlamespeakerAbility1(GameState state)
		{
			super(state, "Whenever Prophetic Flamespeaker deals combat damage to a player, exile the top card of your library. You may play it this turn.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator top = TopCards.instance(1, LibraryOf.instance(You.instance()));
			EventFactory exile = exile(top, "Exile the top card of your library.");
			this.addEffect(exile);

			PlayPermission permission = new PlayPermission(You.instance());
			SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exile));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCard);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
			this.addEffect(createFloatingEffect("You may play it this turn.", part));
		}
	}

	public PropheticFlamespeaker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Double strike, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.DoubleStrike(state));

		// Whenever Prophetic Flamespeaker deals combat damage to a player,
		// exile the top card of your library. You may play it this turn.
		this.addAbility(new PropheticFlamespeakerAbility1(state));
	}
}

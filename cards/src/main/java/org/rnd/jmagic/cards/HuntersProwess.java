package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hunter's Prowess")
@Types({Type.SORCERY})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class HuntersProwess extends Card
{
	public static class HitDraw extends EventTriggeredAbility
	{
		public HitDraw(GameState state)
		{
			super(state, "Whenever this creature deals combat damage to a player, draw that many cards.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(drawCards(You.instance(), thatMany, "Draw that many cards."));
		}
	}

	public HuntersProwess(GameState state)
	{
		super(state);

		// Until end of turn, target creature gets +3/+3 and gains trample and
		// "Whenever this creature deals combat damage to a player, draw that many cards."
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +3, +3,//
				"Until end of turn, target creature gets +3/+3 and gains trample and \"Whenever this creature deals combat damage to a player, draw that many cards.\"",//
				org.rnd.jmagic.abilities.keywords.Trample.class,//
				HitDraw.class));
	}
}

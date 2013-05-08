package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Suffer the Past")
@Types({Type.INSTANT})
@ManaCost("XB")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SufferthePast extends Card
{
	public SufferthePast(GameState state)
	{
		super(state);

		// Exile X target cards from target player's graveyard.
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator targetPlayer = targetedBy(this.addTarget(Players.instance(), "target player"));

		Target targetCards = this.addTarget(InZone.instance(GraveyardOf.instance(targetPlayer)), "X target cards from target player's graveyard");
		targetCards.setSingleNumber(X);

		EventFactory exile = exile(targetedBy(targetCards), "Exile X target cards from target player's graveyard.");
		this.addEffect(exile);
		SetGenerator exiledCards = Intersect.instance(NewObjectOf.instance(EffectResult.instance(exile)), InZone.instance(ExileZone.instance()));
		SetGenerator amount = Count.instance(exiledCards);

		// For each card exiled this way, that player loses 1 life and you gain
		// 1 life.
		this.addEffect(loseLife(targetPlayer, amount, "For each card exiled this way, that player loses 1 life"));
		this.addEffect(gainLife(You.instance(), amount, "and you gain 1 life."));
	}
}

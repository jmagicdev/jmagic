package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind's Desire")
@Types({Type.SORCERY})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MindsDesire extends Card
{
	public MindsDesire(GameState state)
	{
		super(state);

		// Shuffle your library. Then exile the top card of your library. Until
		// end of turn, you may play that card without paying its mana cost. (If
		// it has X in its mana cost, X is 0.)
		SetGenerator you = You.instance();

		this.addEffect(shuffleYourLibrary("Then shuffle your library."));

		EventFactory exile = exile(TopCards.instance(1, LibraryOf.instance(you)), "Then exile the top card of your library.");
		this.addEffect(exile);

		SetGenerator thatCard = NewObjectOf.instance(EffectResult.instance(exile));

		PlayPermission permission = new PlayPermission(you);
		permission.forceAlternateCost(Empty.instance());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCard);
		part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(permission));
		this.addEffect(createFloatingEffect("Until end of turn, you may play that card without paying its mana cost.", part));

		// Storm (When you cast this spell, copy it for each spell cast before
		// it this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}

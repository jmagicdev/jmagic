package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Epic Experiment")
@Types({Type.SORCERY})
@ManaCost("XUR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class EpicExperiment extends Card
{
	public EpicExperiment(GameState state)
	{
		super(state);

		// Exile the top X cards of your library. For each instant and sorcery
		// card with converted mana cost X or less among them, you may cast that
		// card without paying its mana cost. Then put all cards exiled this way
		// that weren't cast into your graveyard.
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator topX = TopCards.instance(X, LibraryOf.instance(You.instance()));

		EventFactory exile = exile(topX, "Exile the top X cards of your library.");
		this.addEffect(exile);

		SetGenerator exiled = NewObjectOf.instance(EffectResult.instance(exile));
		SetGenerator spells = HasType.instance(Type.INSTANT, Type.SORCERY);
		SetGenerator cmcXOrLess = HasConvertedManaCost.instance(Between.instance(null, ValueOfX.instance(This.instance())));
		SetGenerator playable = Intersect.instance(spells, cmcXOrLess, exiled);

		EventFactory factory = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "For each instant and sorcery card with converted mana cost X or less among them, you may cast that card without paying its mana cost.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, playable);
		this.addEffect(factory);

		this.addEffect(putIntoGraveyard(exiled, "Then put all cards exiled this way that weren't cast into your graveyard."));
	}
}

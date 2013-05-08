package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ColossusShuffle extends StaticAbility
{
	private final String cardName;

	public ColossusShuffle(GameState state, String cardName)
	{
		super(state, "If " + cardName + " would be put into a graveyard from anywhere, reveal " + cardName + " and shuffle it into its owner's library instead.");

		this.cardName = cardName;

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "If " + cardName + " would be put into a graveyard from anywhere, reveal " + cardName + " and shuffle it into its owner's library instead", true);
		replacement.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), This.instance(), true));

		SetGenerator replacedMove = replacement.replacedByThis();

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal " + cardName);
		reveal.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
		reveal.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacedMove));
		replacement.addEffect(reveal);

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle it into its owner's library instead");
		shuffle.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedMove));
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(OldObjectOf.instance(replacedMove), OwnerOf.instance(OldObjectOf.instance(replacedMove))));
		replacement.addEffect(shuffle);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}

	@Override
	public ColossusShuffle create(Game game)
	{
		return new ColossusShuffle(game.physicalState, this.cardName);
	}
}
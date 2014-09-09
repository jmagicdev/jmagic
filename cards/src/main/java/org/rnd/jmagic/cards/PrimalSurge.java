package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Primal Surge")
@Types({Type.SORCERY})
@ManaCost("8GG")
@ColorIdentity({Color.GREEN})
public final class PrimalSurge extends Card
{
	public PrimalSurge(GameState state)
	{
		super(state);

		// Exile the top card of your library. If it's a permanent card, you may
		// put it onto the battlefield. If you do, repeat this process.
		EventFactory exile = exile(TopCards.instance(1, LibraryOf.instance(You.instance())), "Exile the top card of your library.");

		SetGenerator result = NewObjectOf.instance(EffectResult.instance(exile));
		EventFactory youMay = youMay(putOntoBattlefield(result, "Put it onto the battlefield."), "You may put it onto the battlefield.");

		SetGenerator ifIsPermanentType = Intersect.instance(TypesOf.instance(result), Identity.fromCollection(Type.permanentTypes()));
		EventFactory ifThen = ifThen(ifIsPermanentType, youMay, "If it's a permanent card, you may put it onto the battlefield.");

		// Use the 'if' condition in case there was no exiled card (in which
		// case the 'you may' event was not created, which would cause
		// EffectResult to use the previous results of the 'you may' event)
		SetGenerator decideToRepeat = Intersect.instance(EffectResult.instance(youMay), Identity.instance(Answer.YES));
		SetGenerator stopCondition = Not.instance(Both.instance(ifIsPermanentType, decideToRepeat));

		EventFactory factory = new EventFactory(REPEAT_THIS_PROCESS, "Exile the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you do, repeat this process.");
		factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(sequence(exile, ifThen)));
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(stopCondition));
		this.addEffect(factory);
	}
}

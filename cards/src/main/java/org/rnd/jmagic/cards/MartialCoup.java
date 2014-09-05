package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Martial Coup")
@Types({Type.SORCERY})
@ManaCost("XWW")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MartialCoup extends Card
{
	public MartialCoup(GameState state)
	{
		super(state);

		// Put X 1/1 white Soldier creature tokens onto the battlefield.
		SetGenerator X = ValueOfX.instance(This.instance());

		CreateTokensFactory tokens = new CreateTokensFactory(X, numberGenerator(1), numberGenerator(1), "Put X 1/1 white Soldier creature tokens onto the battlefield.");
		tokens.setColors(Color.WHITE);
		tokens.setSubTypes(SubType.SOLDIER);
		EventFactory factory = tokens.getEventFactory();
		this.addEffect(factory);

		// If X is 5 or more, destroy all other creatures.
		SetGenerator xIsBig = Intersect.instance(X, Between.instance(5, null));

		SetGenerator thoseTokens = EffectResult.instance(factory);
		SetGenerator allOtherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), thoseTokens);
		EventFactory destroyOtherCreatures = destroy(allOtherCreatures, "Destroy all other creatures.");

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If X is 5 or more, destroy all other creatures.");
		effect.parameters.put(EventType.Parameter.IF, xIsBig);
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(destroyOtherCreatures));
		this.addEffect(effect);
	}
}

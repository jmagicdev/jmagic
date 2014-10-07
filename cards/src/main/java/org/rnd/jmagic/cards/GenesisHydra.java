package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Genesis Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.HYDRA})
@ManaCost("XGG")
@ColorIdentity({Color.GREEN})
public final class GenesisHydra extends Card
{
	public static final class GenesisHydraAbility0 extends EventTriggeredAbility
	{
		public GenesisHydraAbility0(GameState state)
		{
			super(state, "When you cast Genesis Hydra, reveal the top X cards of your library. You may put a nonland permanent card with converted mana cost X or less from among them onto the battlefield. Then shuffle the rest into your library.");
			this.addPattern(whenYouCastThisSpell());
			this.triggersFromStack();

			SetGenerator X = ValueOfX.instance(ABILITY_SOURCE_OF_THIS);

			// i'm not just saying "permanent types that aren't lands" here,
			// because Dryad Arbor isn't a nonland permanent card
			SetGenerator nonlandPermanentCard = RelativeComplement.instance(HasType.instance(Type.permanentTypes()), HasType.instance(Type.LAND));
			SetGenerator cmcXOrLess = HasConvertedManaCost.instance(Between.instance(numberGenerator(0), X));
			SetGenerator available = Intersect.instance(nonlandPermanentCard, cmcXOrLess);

			this.addEffect(Sifter.start().reveal(X).drop(1, available).shuffle().getEventFactory("Reveal the top X cards of your library. You may put a nonland permanent card with converted mana cost X or less from among them onto the battlefield. Then shuffle the rest into your library."));
		}
	}

	public GenesisHydra(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// When you cast Genesis Hydra, reveal the top X cards of your library.
		// You may put a nonland permanent card with converted mana cost X or
		// less from among them onto the battlefield. Then shuffle the rest into
		// your library.
		this.addAbility(new GenesisHydraAbility0(state));

		// Genesis Hydra enters the battlefield with X +1/+1 counters on it.
		SetGenerator X = ValueOfX.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), X, "X +1/+1 counters on it", Counter.CounterType.PLUS_ONE_PLUS_ONE));
	}
}

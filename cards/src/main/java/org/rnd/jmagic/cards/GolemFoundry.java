package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Golem Foundry")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class GolemFoundry extends Card
{
	public static final class GolemFoundryAbility0 extends EventTriggeredAbility
	{
		public GolemFoundryAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may put a charge counter on Golem Foundry.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			this.addEffect(youMay(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Golem Foundry."), "You may put a charge counter on Golem Foundry."));
		}
	}

	public static final class GolemFoundryAbility1 extends ActivatedAbility
	{
		public GolemFoundryAbility1(GameState state)
		{
			super(state, "Remove three charge counters from Golem Foundry: Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.addCost(removeCounters(3, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove three charge counters from Golem Foundry"));
			CreateTokensFactory factory = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.GOLEM);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public GolemFoundry(GameState state)
	{
		super(state);

		// Whenever you cast an artifact spell, you may put a charge counter on
		// Golem Foundry.
		this.addAbility(new GolemFoundryAbility0(state));

		// Remove three charge counters from Golem Foundry: Put a 3/3 colorless
		// Golem artifact creature token onto the battlefield.
		this.addAbility(new GolemFoundryAbility1(state));
	}
}

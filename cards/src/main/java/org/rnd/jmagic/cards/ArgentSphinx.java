package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Argent Sphinx")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class ArgentSphinx extends Card
{
	public static final class ArgentSphinxAbility1 extends ActivatedAbility
	{
		public ArgentSphinxAbility1(GameState state)
		{
			super(state, "Metalcraft \u2014 (U): Exile Argent Sphinx. Return it to the battlefield under your control at the beginning of the next end step. Activate this ability only if you control three or more artifacts.");
			this.setManaCost(new ManaPool("(U)"));

			EventFactory exile = exile(ABILITY_SOURCE_OF_THIS, "Exile Argent Sphinx.");
			this.addEffect(exile);

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return it to the battlefield under your control.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(EffectResult.instance(exile)));

			EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to the battlefield under your control at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(move));
			this.addEffect(factory);

			this.addActivateRestriction(Not.instance(Metalcraft.instance()));
		}
	}

	public ArgentSphinx(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Metalcraft \u2014 (U): Exile Argent Sphinx. Return it to the
		// battlefield under your control at the beginning of the next end step.
		// Activate this ability only if you control three or more artifacts.
		this.addAbility(new ArgentSphinxAbility1(state));
	}
}

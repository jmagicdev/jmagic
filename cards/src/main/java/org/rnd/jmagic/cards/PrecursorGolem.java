package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Precursor Golem")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class PrecursorGolem extends Card
{
	public static final class PrecursorGolemAbility0 extends EventTriggeredAbility
	{
		public PrecursorGolemAbility0(GameState state)
		{
			super(state, "When Precursor Golem enters the battlefield, put two 3/3 colorless Golem artifact creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory tokens = new CreateTokensFactory(2, 3, 3, "Put two 3/3 colorless Golem artifact creature tokens onto the battlefield.");
			tokens.setSubTypes(SubType.GOLEM);
			tokens.setArtifact();
			this.addEffect(tokens.getEventFactory());
		}
	}

	public static final class PrecursorGolemAbility1 extends EventTriggeredAbility
	{
		public PrecursorGolemAbility1(GameState state)
		{
			super(state, "Whenever a player casts an instant or sorcery spell that targets only a single Golem, that player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.");

			SetGenerator golemPermanents = Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.GOLEM));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.withResult(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Intersect.instance(HasOneTarget.instance(), HasTarget.instance(golemPermanents))));
			this.addPattern(pattern);

			EventFactory factory = new EventFactory(EventType.COPY_SPELL_FOR_EACH_TARGET, "That player copies that spell for each other Golem that spell could target. Each copy targets a different one of those Golems.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, EventResult.instance(TriggerEvent.instance(This.instance())));
			factory.parameters.put(EventType.Parameter.TARGET, golemPermanents);
			this.addEffect(factory);
		}
	}

	public PrecursorGolem(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Precursor Golem enters the battlefield, put two 3/3 colorless
		// Golem artifact creature tokens onto the battlefield.
		this.addAbility(new PrecursorGolemAbility0(state));

		// Whenever a player casts an instant or sorcery spell that targets only
		// a single Golem, that player copies that spell for each other Golem
		// that spell could target. Each copy targets a different one of those
		// Golems.
		this.addAbility(new PrecursorGolemAbility1(state));
	}
}

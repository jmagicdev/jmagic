package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wall of Frost")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class WallofFrost extends Card
{
	public static final class DontLickColdPoles extends EventTriggeredAbility
	{
		public DontLickColdPoles(GameState state)
		{
			super(state, "Whenever Wall of Frost blocks a creature, that creature doesn't untap during its controller's next untap step.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			pattern.put(EventType.Parameter.DEFENDER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator thatCreature = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.ATTACKER);
			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(thatCreature);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));

			SetGenerator thatPlayersUntap = UntapStepOf.instance(ControllerOf.instance(thatCreature));
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

			this.addEffect(createFloatingEffect(untapStepOver, "That creature doesn't untap during its controller's next untap step.", part));
		}
	}

	public WallofFrost(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(7);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		// Defender (This creature can't attack.)

		// Whenever Wall of Frost blocks a creature, that creature doesn't untap
		// during its controller's next untap step.
		this.addAbility(new DontLickColdPoles(state));
	}
}

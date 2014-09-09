package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Immerwolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("1RG")
@ColorIdentity({Color.GREEN, Color.RED})
public final class Immerwolf extends Card
{
	public static final class ImmerwolfAbility1 extends StaticAbility
	{
		public ImmerwolfAbility1(GameState state)
		{
			super(state, "Other Wolf and Werewolf creatures you control get +1/+1.");

			SetGenerator affected = RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.WOLF, SubType.WEREWOLF), CREATURES_YOU_CONTROL), This.instance());
			this.addEffectPart(modifyPowerAndToughness(affected, +1, +1));
		}
	}

	public static final class ImmerwolfAbility2 extends StaticAbility
	{
		public ImmerwolfAbility2(GameState state)
		{
			super(state, "Non-Human Werewolves you control can't transform.");

			SetGenerator affected = Intersect.instance(RelativeComplement.instance(HasSubType.instance(SubType.WEREWOLF), HasSubType.instance(SubType.HUMAN)), CREATURES_YOU_CONTROL);

			org.rnd.jmagic.engine.patterns.SimpleEventPattern pattern = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(EventType.TRANSFORM_ONE_PERMANENT);
			pattern.put(EventType.Parameter.OBJECT, affected);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public Immerwolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Other Wolf and Werewolf creatures you control get +1/+1.
		this.addAbility(new ImmerwolfAbility1(state));

		// Non-Human Werewolves you control can't transform.
		this.addAbility(new ImmerwolfAbility2(state));
	}
}

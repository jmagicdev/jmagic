package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hand of the Praetors")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class HandofthePraetors extends Card
{
	public static final class HandofthePraetorsAbility1 extends StaticAbility
	{
		public HandofthePraetorsAbility1(GameState state)
		{
			super(state, "Other creatures you control with infect get +1/+1.");

			SetGenerator affect = RelativeComplement.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasKeywordAbility.instance(Infect.class)), This.instance());
			this.addEffectPart(modifyPowerAndToughness(affect, +1, +1));
		}
	}

	public static final class HandofthePraetorsAbility2 extends EventTriggeredAbility
	{
		public HandofthePraetorsAbility2(GameState state)
		{
			super(state, "Whenever you cast a creature spell with infect, target player gets a poison counter.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), Spells.instance(), HasKeywordAbility.instance(Infect.class)));
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(putCounters(1, Counter.CounterType.POISON, target, "Target player gets a poison counter."));
		}
	}

	public HandofthePraetors(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Other creatures you control with infect get +1/+1.
		this.addAbility(new HandofthePraetorsAbility1(state));

		// Whenever you cast a creature spell with infect, target player gets a
		// poison counter.
		this.addAbility(new HandofthePraetorsAbility2(state));
	}
}

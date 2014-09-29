package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Surrak Dragonclaw")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class SurrakDragonclaw extends Card
{
	public static final class SurrakDragonclawAbility2 extends StaticAbility
	{
		public SurrakDragonclawAbility2(GameState state)
		{
			super(state, "Creature spells you control can't be countered.");

			SetGenerator yourCreatureSpells = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance(), Stack.instance()));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.COUNTER);
			pattern.put(EventType.Parameter.OBJECT, yourCreatureSpells);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));

			this.addEffectPart(part);
		}
	}

	public static final class SurrakDragonclawAbility3 extends StaticAbility
	{
		public SurrakDragonclawAbility3(GameState state)
		{
			super(state, "Other creatures you control have trample.");
			SetGenerator otherCreatures = RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance());
			this.addEffectPart(addAbilityToObject(otherCreatures, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public SurrakDragonclaw(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Surrak Dragonclaw can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Creature spells you control can't be countered.
		this.addAbility(new SurrakDragonclawAbility2(state));

		// Other creatures you control have trample.
		this.addAbility(new SurrakDragonclawAbility3(state));
	}
}

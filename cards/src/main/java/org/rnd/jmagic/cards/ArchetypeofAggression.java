package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archetype of Aggression")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class ArchetypeofAggression extends Card
{
	public static final class ArchetypeofAggressionAbility0 extends StaticAbility
	{
		public ArchetypeofAggressionAbility0(GameState state)
		{
			super(state, "Creatures you control have trample.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public static final class ArchetypeofAggressionAbility1 extends StaticAbility
	{
		public ArchetypeofAggressionAbility1(GameState state)
		{
			super(state, "Creatures your opponents control lose trample and can't have or gain trample.");

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_HAVE_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enemyCreatures);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Trample.class));
			this.addEffectPart(part);
		}
	}

	public ArchetypeofAggression(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Creatures you control have trample.
		this.addAbility(new ArchetypeofAggressionAbility0(state));

		// Creatures your opponents control lose trample and can't have or gain
		// trample.
		this.addAbility(new ArchetypeofAggressionAbility1(state));
	}
}

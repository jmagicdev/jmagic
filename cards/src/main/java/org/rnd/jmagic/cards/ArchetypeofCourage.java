package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archetype of Courage")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class ArchetypeofCourage extends Card
{
	public static final class ArchetypeofCourageAbility0 extends StaticAbility
	{
		public ArchetypeofCourageAbility0(GameState state)
		{
			super(state, "Creatures you control have first strike.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public static final class ArchetypeofCourageAbility1 extends StaticAbility
	{
		public ArchetypeofCourageAbility1(GameState state)
		{
			super(state, "Creatures your opponents control lose first strike and can't have or gain first strike.");

			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_HAVE_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enemyCreatures);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.FirstStrike.class));
			this.addEffectPart(part);
		}
	}

	public ArchetypeofCourage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Creatures you control have first strike.
		this.addAbility(new ArchetypeofCourageAbility0(state));

		// Creatures your opponents control lose first strike and can't have or
		// gain first strike.
		this.addAbility(new ArchetypeofCourageAbility1(state));
	}
}

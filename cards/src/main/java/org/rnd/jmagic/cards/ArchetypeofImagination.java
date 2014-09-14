package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archetype of Imagination")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class ArchetypeofImagination extends Card
{
	public static final class ArchetypeofImaginationAbility0 extends StaticAbility
	{
		public ArchetypeofImaginationAbility0(GameState state)
		{
			super(state, "Creatures you control have flying.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public static final class ArchetypeofImaginationAbility1 extends StaticAbility
	{
		public ArchetypeofImaginationAbility1(GameState state)
		{
			super(state, "Creatures your opponents control lose flying and can't have or gain flying.");
			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_HAVE_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enemyCreatures);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffectPart(part);
		}
	}

	public ArchetypeofImagination(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Creatures you control have flying.
		this.addAbility(new ArchetypeofImaginationAbility0(state));

		// Creatures your opponents control lose flying and can't have or gain
		// flying.
		this.addAbility(new ArchetypeofImaginationAbility1(state));
	}
}

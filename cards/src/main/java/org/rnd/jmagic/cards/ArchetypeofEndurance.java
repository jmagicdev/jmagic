package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archetype of Endurance")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.BOAR})
@ManaCost("6GG")
@ColorIdentity({Color.GREEN})
public final class ArchetypeofEndurance extends Card
{
	public static final class ArchetypeofEnduranceAbility0 extends StaticAbility
	{
		public ArchetypeofEnduranceAbility0(GameState state)
		{
			super(state, "Creatures you control have hexproof.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Hexproof.class));
		}
	}

	public static final class ArchetypeofEnduranceAbility1 extends StaticAbility
	{
		public ArchetypeofEnduranceAbility1(GameState state)
		{
			super(state, "Creatures your opponents control lose hexproof and can't have or gain hexproof.");
			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_HAVE_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enemyCreatures);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Hexproof.class));
			this.addEffectPart(part);
		}
	}

	public ArchetypeofEndurance(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Creatures you control have hexproof.
		this.addAbility(new ArchetypeofEnduranceAbility0(state));

		// Creatures your opponents control lose hexproof and can't have or gain
		// hexproof.
		this.addAbility(new ArchetypeofEnduranceAbility1(state));
	}
}

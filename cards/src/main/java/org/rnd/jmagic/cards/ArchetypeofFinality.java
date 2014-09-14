package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archetype of Finality")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.GORGON})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ArchetypeofFinality extends Card
{
	public static final class ArchetypeofFinalityAbility0 extends StaticAbility
	{
		public ArchetypeofFinalityAbility0(GameState state)
		{
			super(state, "Creatures you control have deathtouch.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public static final class ArchetypeofFinalityAbility1 extends StaticAbility
	{
		public ArchetypeofFinalityAbility1(GameState state)
		{
			super(state, "Creatures your opponents control lose deathtouch and can't have or gain deathtouch.");
			SetGenerator enemyCreatures = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasType.instance(Type.CREATURE));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_HAVE_KEYWORD);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enemyCreatures);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Deathtouch.class));
			this.addEffectPart(part);
		}
	}

	public ArchetypeofFinality(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Creatures you control have deathtouch.
		this.addAbility(new ArchetypeofFinalityAbility0(state));

		// Creatures your opponents control lose deathtouch and can't have or
		// gain deathtouch.
		this.addAbility(new ArchetypeofFinalityAbility1(state));
	}
}

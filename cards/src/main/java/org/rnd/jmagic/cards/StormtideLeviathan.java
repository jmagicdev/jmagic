package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stormtide Leviathan")
@Types({Type.CREATURE})
@SubTypes({SubType.LEVIATHAN})
@ManaCost("5UUU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class StormtideLeviathan extends Card
{
	public static final class StormtideLeviathanAbility1 extends StaticAbility
	{
		public StormtideLeviathanAbility1(GameState state)
		{
			super(state, "All lands are Islands in addition to their other types.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, LandPermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ISLAND));
			this.addEffectPart(part);
		}
	}

	public static final class StormtideLeviathanAbility2 extends StaticAbility
	{
		public StormtideLeviathanAbility2(GameState state)
		{
			super(state, "Creatures without flying or islandwalk can't attack.");

			SetGenerator evasiveCreatures = Union.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class));
			SetGenerator nonEvasiveCreatures = RelativeComplement.instance(CreaturePermanents.instance(), evasiveCreatures);
			SetGenerator restriction = Intersect.instance(nonEvasiveCreatures, Attacking.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public StormtideLeviathan(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Islandwalk (This creature is unblockable as long as defending player
		// controls an Island.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// All lands are Islands in addition to their other types.
		this.addAbility(new StormtideLeviathanAbility1(state));

		// Creatures without flying or islandwalk can't attack.
		this.addAbility(new StormtideLeviathanAbility2(state));
	}
}

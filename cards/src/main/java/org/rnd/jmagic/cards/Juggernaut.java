package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Juggernaut")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.JUGGERNAUT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Juggernaut extends Card
{
	public static final class CantBeBlockedByWalls extends StaticAbility
	{
		public CantBeBlockedByWalls(GameState state)
		{
			super(state, "Juggernaut can't be blocked by Walls.");

			SetGenerator blockedByWalls = BlockedBy.instance(HasSubType.instance(SubType.WALL));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(blockedByWalls, This.instance())));
			this.addEffectPart(part);
		}
	}

	public Juggernaut(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Juggernaut attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));

		// Juggernaut can't be blocked by Walls.
		this.addAbility(new CantBeBlockedByWalls(state));
	}
}

package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sea Monster")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SeaMonster extends Card
{
	public static final class IslandAttacker extends StaticAbility
	{
		public IslandAttacker(GameState state)
		{
			super(state, "Sea Monster can't attack unless defending player controls an Island.");

			SetGenerator playersWithIslands = ControllerOf.instance(Intersect.instance(InZone.instance(Battlefield.instance()), HasSubType.instance(SubType.ISLAND)));
			SetGenerator playersWithoutIslands = RelativeComplement.instance(Players.instance(), playersWithIslands);
			SetGenerator restriction = Intersect.instance(This.instance(), Attacking.instance(playersWithoutIslands));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public SeaMonster(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new IslandAttacker(state));
	}
}

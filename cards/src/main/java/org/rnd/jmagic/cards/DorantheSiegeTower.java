package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Doran, the Siege Tower")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.TREEFOLK})
@ManaCost("BGW")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class DorantheSiegeTower extends Card
{
	public static final class DorantheSiegeTowerAbility0 extends StaticAbility
	{
		public DorantheSiegeTowerAbility0(GameState state)
		{
			super(state, "Each creature assigns combat damage equal to its toughness rather than its power.");
			this.addEffectPart(new ContinuousEffect.Part(ContinuousEffectType.ASSIGN_COMBAT_DAMAGE_USING_TOUGHNESS));
		}
	}

	public DorantheSiegeTower(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Each creature assigns combat damage equal to its toughness rather
		// than its power.
		this.addAbility(new DorantheSiegeTowerAbility0(state));
	}
}

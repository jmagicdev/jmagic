package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Farbog Boneflinger")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class FarbogBoneflinger extends Card
{
	public static final class FarbogBoneflingerAbility0 extends EventTriggeredAbility
	{
		public FarbogBoneflingerAbility0(GameState state)
		{
			super(state, "When Farbog Boneflinger enters the battlefield, target creature gets -2/-2 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, -2, -2, "Target creature gets -2/-2 until end of turn."));
		}
	}

	public FarbogBoneflinger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Farbog Boneflinger enters the battlefield, target creature gets
		// -2/-2 until end of turn.
		this.addAbility(new FarbogBoneflingerAbility0(state));
	}
}

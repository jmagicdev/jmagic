package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Legacy Weapon")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("7")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.RED, Color.GREEN})
public final class LegacyWeapon extends Card
{
	public static final class Removal extends ActivatedAbility
	{
		public Removal(GameState state)
		{
			super(state, "(W)(U)(B)(R)(G): Exile target permanent.");
			this.setManaCost(new ManaPool("WUBRG"));
			Target target = this.addTarget(InZone.instance(Battlefield.instance()), "target permanent");
			this.addEffect(exile(targetedBy(target), "Exile target permanent."));
		}
	}

	public LegacyWeapon(GameState state)
	{
		super(state);

		this.addAbility(new Removal(state));
		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}

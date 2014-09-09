package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vault Skirge")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.IMP})
@ManaCost("1(B/P)")
@ColorIdentity({Color.BLACK})
public final class VaultSkirge extends Card
{
	public VaultSkirge(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}

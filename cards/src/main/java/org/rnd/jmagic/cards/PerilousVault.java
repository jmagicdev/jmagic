package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Perilous Vault")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class PerilousVault extends Card
{
	public static final class PerilousVaultAbility0 extends ActivatedAbility
	{
		public PerilousVaultAbility0(GameState state)
		{
			super(state, "(5), (T), Exile Perilous Vault: Exile all nonland permanents.");
			this.setManaCost(new ManaPool("(5)"));
			this.costsTap = true;
			this.addCost(exileThis("Perilous Vault"));

			SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			this.addEffect(exile(nonlandPermanents, "Exile all nonland permanents."));
		}
	}

	public PerilousVault(GameState state)
	{
		super(state);

		// (5), (T), Exile Perilous Vault: Exile all nonland permanents.
		this.addAbility(new PerilousVaultAbility0(state));
	}
}

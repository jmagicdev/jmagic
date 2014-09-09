package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auriok Salvagers")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class AuriokSalvagers extends Card
{
	public static final class AuriokSalvagersAbility0 extends ActivatedAbility
	{
		public AuriokSalvagersAbility0(GameState state)
		{
			super(state, "(1)(W): Return target artifact card with converted mana cost 1 or less from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(1)(W)"));

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), HasConvertedManaCost.instance(Between.instance(null, 1)), InZone.instance(yourGraveyard)), "target artifact card with converted mana cost 1 or less in your graveyard"));

			putIntoHand(target, You.instance(), "Return target artifact card with converted mana cost 1 or less from your graveyard to your hand.");
		}
	}

	public AuriokSalvagers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// (1)(W): Return target artifact card with converted mana cost 1 or
		// less from your graveyard to your hand.
		this.addAbility(new AuriokSalvagersAbility0(state));
	}
}

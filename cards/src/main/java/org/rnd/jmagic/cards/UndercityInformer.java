package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undercity Informer")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class UndercityInformer extends Card
{
	public static final class UndercityInformerAbility0 extends ActivatedAbility
	{
		public UndercityInformerAbility0(GameState state)
		{
			super(state, "(1), Sacrifice a creature: Target player reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeACreature());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator library = LibraryOf.instance(target);
			SetGenerator cards = TopMost.instance(library, numberGenerator(1), HasType.instance(Type.LAND));

			this.addEffect(putIntoGraveyard(cards, "Target player reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard."));
		}
	}

	public UndercityInformer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (1), Sacrifice a creature: Target player reveals cards from the top
		// of his or her library until he or she reveals a land card, then puts
		// those cards into his or her graveyard.
		this.addAbility(new UndercityInformerAbility0(state));
	}
}

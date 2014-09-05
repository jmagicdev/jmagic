package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hurkyl's Recall")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Antiquities.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class HurkylsRecall extends Card
{
	public HurkylsRecall(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator toBounce = Intersect.instance(ArtifactPermanents.instance(), OwnedBy.instance(targetedBy(target)));
		this.addEffect(bounce(toBounce, "Return all artifacts target player owns to his or her hand."));
	}
}

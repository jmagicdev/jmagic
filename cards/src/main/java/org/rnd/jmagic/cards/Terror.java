package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Terror")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter2000.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Terror extends Card
{
	public Terror(GameState state)
	{
		super(state);

		SetGenerator nonartifactCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT));
		SetGenerator nonartifactNonblackCreatures = RelativeComplement.instance(nonartifactCreatures, HasColor.instance(Color.BLACK));
		Target target = this.addTarget(nonartifactNonblackCreatures, "target nonartifact, nonblack creature");
		this.addEffects(bury(this, targetedBy(target), "Destroy target nonartifact, nonblack creature. It can't be regenerated."));
	}
}

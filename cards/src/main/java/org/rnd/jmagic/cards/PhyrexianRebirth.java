package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Phyrexian Rebirth")
@Types({Type.SORCERY})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PhyrexianRebirth extends Card
{
	public PhyrexianRebirth(GameState state)
	{
		super(state);

		// Destroy all creatures, then put an X/X colorless Horror artifact
		// creature token onto the battlefield, where X is the number of
		// creatures destroyed this way.
		EventFactory destroy = destroy(CreaturePermanents.instance(), "Destroy all creatures,");
		this.addEffect(destroy);

		SetGenerator X = Count.instance(NewObjectOf.instance(EffectResult.instance(destroy)));

		CreateTokensFactory factory = new CreateTokensFactory(numberGenerator(1), "then put an X/X colorless Horror artifact creature token onto the battlefield, where X is the number of creatures destroyed this way.");
		factory.addCreature(X, X);
		factory.setSubTypes(SubType.HORROR);
		factory.setArtifact();
		this.addEffect(factory.getEventFactory());
	}
}

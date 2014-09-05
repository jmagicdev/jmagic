package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Chalice of Life")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
@BackFace(ChaliceofDeath.class)
public final class ChaliceofLife extends Card
{
	public static final class ChaliceofLifeAbility0 extends ActivatedAbility
	{
		public ChaliceofLifeAbility0(GameState state)
		{
			super(state, "(T): You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.");
			this.costsTap = true;

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));

			SetGenerator condition = Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(Sum.instance(Union.instance(StartingLifeTotalOf.instance(You.instance()), numberGenerator(10))), null));
			this.addEffect(ifThen(condition, transform(ABILITY_SOURCE_OF_THIS, "Transform Chalice of Life."), "If you have at least 10 life more than your starting life total, transform Chalice of Life."));
		}
	}

	public ChaliceofLife(GameState state)
	{
		super(state);

		// (T): You gain 1 life. Then if you have at least 10 life more than
		// your starting life total, transform Chalice of Life.
		this.addAbility(new ChaliceofLifeAbility0(state));
	}
}

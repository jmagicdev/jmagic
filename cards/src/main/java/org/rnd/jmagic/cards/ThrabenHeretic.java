package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thraben Heretic")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ThrabenHeretic extends Card
{
	public static final class ThrabenHereticAbility0 extends ActivatedAbility
	{
		public ThrabenHereticAbility0(GameState state)
		{
			super(state, "(T): Exile target creature card from a graveyard.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(InZone.instance(GraveyardOf.instance(Players.instance())), HasType.instance(Type.CREATURE)), "target creature card in a graveyard"));
			this.addEffect(exile(target, "Exile target creature card from a graveyard."));
		}
	}

	public ThrabenHeretic(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Exile target creature card from a graveyard.
		this.addAbility(new ThrabenHereticAbility0(state));
	}
}

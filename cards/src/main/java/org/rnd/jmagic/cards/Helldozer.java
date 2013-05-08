package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Helldozer")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.GIANT})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Helldozer extends Card
{
	public static final class HelldozerAbility0 extends ActivatedAbility
	{
		public HelldozerAbility0(GameState state)
		{
			super(state, "(B)(B)(B), (T): Destroy target land. If that land was nonbasic, untap Helldozer.");
			this.setManaCost(new ManaPool("(B)(B)(B)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
			this.addEffect(destroy(target, "Destroy target land."));

			EventFactory untap = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If that land was nonbasic, untap Helldozer.");
			untap.parameters.put(EventType.Parameter.IF, RelativeComplement.instance(target, HasSuperType.instance(SuperType.BASIC)));
			untap.parameters.put(EventType.Parameter.THEN, Identity.instance(untap(ABILITY_SOURCE_OF_THIS, "Untap Helldozer")));
			this.addEffect(untap);
		}
	}

	public Helldozer(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// (B)(B)(B), (T): Destroy target land. If that land was nonbasic, untap
		// Helldozer.
		this.addAbility(new HelldozerAbility0(state));
	}
}

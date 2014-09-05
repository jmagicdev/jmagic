package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kor Outfitter")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KorOutfitter extends Card
{
	public static final class GoToOldNavy extends EventTriggeredAbility
	{
		public GoToOldNavy(GameState state)
		{
			super(state, "When Kor Outfitter enters the battlefield, you may attach target Equipment you control to target creature you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target equipment = this.addTarget(Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), ControlledBy.instance(You.instance())), "target Equipment you control");
			Target creature = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");

			EventFactory attach = attach(targetedBy(equipment), targetedBy(creature), "Attach target Equipment you control to target creature you control");
			this.addEffect(youMay(attach, "You may attach target Equipment you control to target creature you control."));
		}
	}

	public KorOutfitter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Kor Outfitter enters the battlefield, you may attach target
		// Equipment you control to target creature you control.
		this.addAbility(new GoToOldNavy(state));
	}
}

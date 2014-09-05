package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phyrexian Revoker")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORROR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class PhyrexianRevoker extends Card
{
	public static final class PhyrexianRevokerAbility0 extends StaticAbility
	{
		public PhyrexianRevokerAbility0(GameState state)
		{
			super(state, "As Phyrexian Revoker enters the battlefield, name a nonland card.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Name a nonland card");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(PithingNeedle.NameACard.DAMMIT_ANOTHER_CUSTOM_EVENTTYPE, "Name a nonland card.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, NonLandCardNames.instance());
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();

			this.getLinkManager().addLinkClass(PhyrexianRevokerAbility1.class);
		}
	}

	public static final class PhyrexianRevokerAbility1 extends StaticAbility
	{
		public PhyrexianRevokerAbility1(GameState state)
		{
			super(state, "Activated abilities of sources with the chosen name can't be activated.");

			SetGenerator hasChosenName = HasName.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(hasChosenName));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(PhyrexianRevokerAbility0.class);
		}
	}

	public PhyrexianRevoker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// As Phyrexian Revoker enters the battlefield, name a nonland card.
		this.addAbility(new PhyrexianRevokerAbility0(state));

		// Activated abilities of sources with the chosen name can't be
		// activated.
		this.addAbility(new PhyrexianRevokerAbility1(state));
	}
}
